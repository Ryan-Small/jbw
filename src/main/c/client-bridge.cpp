#include <jni.h>
#include <BWTA.h>
#include <BWAPI.h>
#include <BWAPI/Client.h>

#define _USE_MATH_DEFINES
#include <math.h>

#include "com_harbinger_jbw_Broodwar.h"
#include "com_harbinger_jbw_Unit.h"

#define JNI_NULL 0

using namespace BWAPI;

// java callback vars
JNIEnv *jEnv;
jobject classref;
void javaPrint(const char* msg);
jmethodID printCallback;

// mapping of IDs to types
std::map<int, UnitType> unitTypeMap;
std::map<int, Race> raceTypeMap;
std::map<int, TechType> techTypeMap;
std::map<int, UpgradeType> upgradeTypeMap;
std::map<int, WeaponType> weaponTypeMap;
std::map<int, UnitSizeType> unitSizeTypeMap;
std::map<int, BulletType> bulletTypeMap;
std::map<int, DamageType> damageTypeMap;
std::map<int, ExplosionType> explosionTypeMap;
std::map<int, UnitCommandType> unitCommandTypeMap;
std::map<int, Order> orderTypeMap;

// region IDs
std::map<BWTA::Region*, int> regionMap;

// data buffer for c++ -> Java data
jint *intBuf;
const int bufferSize = 5000000;

void reconnect(void);
void loadTypeData(void);
bool keyState[256];

// conversion ratios
double TO_DEGREES = 180.0 / M_PI;
double fixedScale = 100.0;

/**
* Entry point from Java
*/
JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_nativeConnect(JNIEnv* env, jobject jObj, jobject classRef)
{
	// get the java callback functions
	jEnv = env;
	classref = classRef;
	jclass jc = env->GetObjectClass(classRef);
	printCallback = env->GetMethodID(jc, "javaPrint", "(Ljava/lang/String;)V");

	javaPrint("BWAPI Client launched!");
	jmethodID connectedCallback = env->GetMethodID(jc, "connected", "()V");
	jmethodID gameStartCallback = env->GetMethodID(jc, "gameStarted", "()V");
	jmethodID gameUpdateCallback = env->GetMethodID(jc, "gameUpdate", "()V");
	jmethodID gameEndCallback = env->GetMethodID(jc, "gameEnded", "()V");
	jmethodID eventCallback = env->GetMethodID(jc, "eventOccurred", "(IIILjava/lang/String;)V");
	jmethodID keyPressCallback = env->GetMethodID(jc, "keyPressed", "(I)V");

	// allocate room for return data structure
	intBuf = new jint[bufferSize];

	// connet to BWAPI
	BWAPI::BWAPI_init();
	javaPrint("Connecting...");
	reconnect();
	loadTypeData();
	env->CallObjectMethod(classref, connectedCallback);

	// hold on to this thread forever. Notify java through callbacks.
	while (true) {
		// wait for a game to start
		if (Broodwar != NULL) {
			javaPrint("Waiting to enter match...");
			while (!Broodwar->isInGame()) {
				BWAPI::BWAPIClient.update();
				if (Broodwar == NULL) {
					return;
				}
			}
		}
		javaPrint("Starting match!");
		env->CallObjectMethod(classref, gameStartCallback);

		// in game
		while (Broodwar->isInGame()) {
			// update client data before event callbacks
			env->CallObjectMethod(classref, gameUpdateCallback);

			// process events
			// BWAPI will always issue a MatchStart event as the very first event of a match
			// BWAPI will always issue a MatchFrame event as the very last event of a frame (second-last at MatchEnd)
			// BWAPI will always issue a MatchEnd event as the very last event of a match
			for (std::list<Event>::iterator e = Broodwar->getEvents().begin(); e != Broodwar->getEvents().end(); ++e) {
				switch (e->getType()) {
				case EventType::MatchStart:
					env->CallObjectMethod(classref, eventCallback, e->getType(), 0, 0, JNI_NULL);
					break;
				case EventType::MatchEnd:
					env->CallObjectMethod(classref, eventCallback, e->getType(), e->isWinner() ? 1 : 0, 0, JNI_NULL);
					break;
				case EventType::MatchFrame:
					env->CallObjectMethod(classref, eventCallback, e->getType(), 0, 0, JNI_NULL);
					break;
				case EventType::MenuFrame:
					env->CallObjectMethod(classref, eventCallback, e->getType(), 0, 0, JNI_NULL);
					break;
				case EventType::SendText: {
					jstring string = env->NewStringUTF(e->getText().c_str());
					env->CallObjectMethod(classref, eventCallback, e->getType(), 0, 0, string);
					env->DeleteLocalRef(string);
					}
					break;
				case EventType::ReceiveText: {
					jstring string = env->NewStringUTF(e->getText().c_str());
					env->CallObjectMethod(classref, eventCallback, e->getType(), 0, 0, string);
					env->DeleteLocalRef(string);
					}
					break;
				case EventType::PlayerLeft:
					env->CallObjectMethod(classref, eventCallback, e->getType(), e->getPlayer()->getID(), 0, JNI_NULL);
					break;
				case EventType::NukeDetect:
					if (e->getPosition() != Positions::Unknown) {
						env->CallObjectMethod(classref, eventCallback, e->getType(), e->getPosition().x(), e->getPosition().y(), JNI_NULL);
					} else {
						env->CallObjectMethod(classref, eventCallback, e->getType(), -1, -1, JNI_NULL);
					}
					break;
				case EventType::UnitDiscover:
					env->CallObjectMethod(classref, eventCallback, e->getType(), e->getUnit()->getID(), 0, JNI_NULL);
					break;
				case EventType::UnitEvade:
					env->CallObjectMethod(classref, eventCallback, e->getType(), e->getUnit()->getID(), 0, JNI_NULL);
					break;
				case EventType::UnitShow:
					env->CallObjectMethod(classref, eventCallback, e->getType(), e->getUnit()->getID(), 0, JNI_NULL);
					break;
				case EventType::UnitHide:
					env->CallObjectMethod(classref, eventCallback, e->getType(), e->getUnit()->getID(), 0, JNI_NULL);
					break;
				case EventType::UnitCreate:
					env->CallObjectMethod(classref, eventCallback, e->getType(), e->getUnit()->getID(), 0, JNI_NULL);
					break;
				case EventType::UnitDestroy:
					env->CallObjectMethod(classref, eventCallback, e->getType(), e->getUnit()->getID(), 0, JNI_NULL);
					break;
				case EventType::UnitMorph:
					env->CallObjectMethod(classref, eventCallback, e->getType(), e->getUnit()->getID(), 0, JNI_NULL);
					break;
				case EventType::UnitRenegade:
					env->CallObjectMethod(classref, eventCallback, e->getType(), e->getUnit()->getID(), 0, JNI_NULL);
					break;
				case EventType::SaveGame: {
					jstring string = env->NewStringUTF(e->getText().c_str());
					env->CallObjectMethod(classref, eventCallback, e->getType(), 0, 0, string);
					env->DeleteLocalRef(string);
					}
					break;
				case EventType::UnitComplete:
					env->CallObjectMethod(classref, eventCallback, e->getType(), e->getUnit()->getID(), 0, JNI_NULL);
					break;
				case EventType::PlayerDropped:
					env->CallObjectMethod(classref, eventCallback, e->getType(), e->getPlayer()->getID(), 0, JNI_NULL);
					break;
				case EventType::None:
					env->CallObjectMethod(classref, eventCallback, e->getType(), 0, 0, JNI_NULL);
					break;
				}
			}

			// check for key presses
			for (int keyCode = 0; keyCode <= 0xff; ++keyCode) {	
				if (Broodwar->getKeyState(keyCode)) {	
					if (!keyState[keyCode]) {
						env->CallObjectMethod(classref, keyPressCallback, keyCode);
					}
					keyState[keyCode] = true;
				} else {
					keyState[keyCode] = false;
				}
			}

			// wait for the next frame
			BWAPI::BWAPIClient.update();
			if (!BWAPI::BWAPIClient.isConnected()) {
				javaPrint("Reconnecting...\n");
				reconnect();
			}
		}

		// game completed
		javaPrint("Match ended");
		env->CallObjectMethod(classref, gameEndCallback);
	}
}

void reconnect(void)
{
	while (!BWAPIClient.connect()) {
		Sleep(1000);
	}
}

void javaPrint(const char* msg) 
{
	jEnv->CallObjectMethod(classref, printCallback, jEnv->NewStringUTF(msg));
}

// build type mappings
void loadTypeData(void) 
{
	std::set<UnitType> types = UnitTypes::allUnitTypes();
	for (std::set<UnitType>::iterator i = types.begin(); i != types.end(); ++i) {
		unitTypeMap[i->getID()] = (*i);
	}

	std::set<Race> raceTypes = Races::allRaces();
	for (std::set<Race>::iterator i = raceTypes.begin(); i != raceTypes.end(); ++i) {
		raceTypeMap[i->getID()] = (*i);
	}

	std::set<TechType> techTypes = TechTypes::allTechTypes();
	for (std::set<TechType>::iterator i = techTypes.begin(); i != techTypes.end(); ++i) {
		techTypeMap[i->getID()] = (*i);
	}

	std::set<UpgradeType> upgradeTypes = UpgradeTypes::allUpgradeTypes();
	for (std::set<UpgradeType>::iterator i = upgradeTypes.begin(); i != upgradeTypes.end(); ++i) {
		upgradeTypeMap[i->getID()] = (*i);
	}

	std::set<WeaponType> weaponTypes = WeaponTypes::allWeaponTypes();
	for (std::set<WeaponType>::iterator i = weaponTypes.begin(); i != weaponTypes.end(); ++i) {
		weaponTypeMap[i->getID()] = (*i);
	}

	std::set<UnitSizeType> unitSizeTypes = UnitSizeTypes::allUnitSizeTypes();
	for (std::set<UnitSizeType>::iterator i = unitSizeTypes.begin(); i != unitSizeTypes.end(); ++i) {
		unitSizeTypeMap[i->getID()] = (*i);
	}

	std::set<BulletType> bulletTypes = BulletTypes::allBulletTypes();
	for (std::set<BulletType>::iterator i = bulletTypes.begin(); i != bulletTypes.end(); ++i) {
		bulletTypeMap[i->getID()] = (*i);
	}

	std::set<DamageType> damageTypes = DamageTypes::allDamageTypes();
	for (std::set<DamageType>::iterator i = damageTypes.begin(); i != damageTypes.end(); ++i) {
		damageTypeMap[i->getID()] = (*i);
	}

	std::set<ExplosionType> explosionTypes = ExplosionTypes::allExplosionTypes();
	for (std::set<ExplosionType>::iterator i = explosionTypes.begin(); i != explosionTypes.end(); ++i) {
		explosionTypeMap[i->getID()] = (*i);
	}

	std::set<UnitCommandType> unitCommandTypes = UnitCommandTypes::allUnitCommandTypes();
	for (std::set<UnitCommandType>::iterator i = unitCommandTypes.begin(); i != unitCommandTypes.end(); ++i) {
		unitCommandTypeMap[i->getID()] = (*i);
	}

	std::set<Order> orders = Orders::allOrders();
	for (std::set<Order>::iterator i = orders.begin(); i != orders.end(); ++i) {
		orderTypeMap[i->getID()] = (*i);
	}
}

/*****************************************************************************************************************/
// Game options
/*****************************************************************************************************************/

JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_nativeEnableUserInput(JNIEnv* env, jobject jObj)
{
	Broodwar->enableFlag(Flag::UserInput);
}

JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_nativeEnablePerfectInformation(JNIEnv* env, jobject jObj)
{
	Broodwar->enableFlag(Flag::CompleteMapInformation);
}

JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_setFrameDelay(JNIEnv* env, jobject jObj, jint speed)
{
	Broodwar->setLocalSpeed(speed);
}

JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_setFrameSkip(JNIEnv* env, jobject jObj, jint skip)
{
	Broodwar->setFrameSkip(skip);
}

JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_leaveGame(JNIEnv* env, jobject jObj)
{
	Broodwar->leaveGame();
}

/*****************************************************************************************************************/
// Game state queries
/*****************************************************************************************************************/

JNIEXPORT jint JNICALL Java_com_harbinger_jbw_Broodwar_getFrame(JNIEnv* env, jobject jObj)
{
	return Broodwar->getFrameCount();
}

JNIEXPORT jint JNICALL Java_com_harbinger_jbw_Broodwar_getReplayFrameTotal(JNIEnv* env, jobject jObj)
{
	return Broodwar->getReplayFrameCount();
}

JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getPlayersData(JNIEnv* env, jobject jObj)
{
	int index = 0;

	std::set<Player*> players = Broodwar->getPlayers();
	
	for (std::set<Player*>::iterator i = players.begin(); i != players.end(); ++i) {
		intBuf[index++] = (*i)->getID();
		intBuf[index++] = (*i)->getRace().getID();
		intBuf[index++] = (*i)->getType().getID();
		intBuf[index++] = (*i)->getStartLocation().x();
		intBuf[index++] = (*i)->getStartLocation().y();
		if (Broodwar->isReplay()) {
			intBuf[index++] = 0; // there is no self in replays
			intBuf[index++] = 0; // as there is no self in replays, there are also no allies
			intBuf[index++] = 0; // as there is no self in replays, there are also no enemies
		} else {
			intBuf[index++] = ((*i)->getID() == Broodwar->self()->getID()) ? 1 : 0;	// is self
			intBuf[index++] = (*i)->isAlly(Broodwar->self()) ? 1 : 0; // is ally
			intBuf[index++] = (*i)->isEnemy(Broodwar->self()) ? 1 : 0; // is enemy
		}
		intBuf[index++] = (*i)->isNeutral() ? 1 : 0;
		intBuf[index++] = (*i)->isObserver() ? 1 : 0; // Always true? BWAPI bug?
		intBuf[index++] = (*i)->getColor().getID();
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getPlayerUpdate(JNIEnv* env, jobject jObj, jint playerID)
{
	int index = 0;
	Player* p = Broodwar->getPlayer(playerID);
	intBuf[index++] = p->minerals();
	intBuf[index++] = p->gas();
	intBuf[index++] = p->supplyUsed();
	intBuf[index++] = p->supplyTotal();
	intBuf[index++] = p->gatheredMinerals();
	intBuf[index++] = p->gatheredGas();
	intBuf[index++] = p->getUnitScore();
	intBuf[index++] = p->getKillScore();
	intBuf[index++] = p->getBuildingScore();
	intBuf[index++] = p->getRazingScore();

	jintArray result =env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

JNIEXPORT jbyteArray JNICALL Java_com_harbinger_jbw_Broodwar_getPlayerName(JNIEnv* env, jobject jObj, jint playerID)
{
	// NewStringUTF causes issues with unusual characters like Korean symbols
	std::string str = Broodwar->getPlayer(playerID)->getName();
	jbyteArray jbArray = env->NewByteArray(str.length());
	env->SetByteArrayRegion(jbArray, 0, str.length(), (jbyte*)str.c_str());
	return jbArray;
}

JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getResearchStatus(JNIEnv* env, jobject jObj, jint playerID)
{
	int index = 0;
	Player* p = Broodwar->getPlayer(playerID);

	std::set<TechType> techTypes = TechTypes::allTechTypes();
	for (std::set<TechType>::iterator i = techTypes.begin(); i != techTypes.end(); ++i) {
		intBuf[index++] = i->getID();
		intBuf[index++] = p->hasResearched((*i)) ? 1 : 0;
		intBuf[index++] = p->isResearching((*i)) ? 1 : 0;
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getUpgradeStatus(JNIEnv* env, jobject jObj, jint playerID)
{
	int index = 0;
	Player* p = Broodwar->getPlayer(playerID);

	std::set<UpgradeType> upTypes = UpgradeTypes::allUpgradeTypes();
	for (std::set<UpgradeType>::iterator i = upTypes.begin(); i != upTypes.end(); ++i) {
		intBuf[index++] = i->getID();
		intBuf[index++] = p->getUpgradeLevel((*i));
		intBuf[index++] = p->isUpgrading((*i)) ? 1 : 0;
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getUnitTypes(JNIEnv* env, jobject jObj)
{
	int index = 0;

	std::set<UnitType> types = UnitTypes::allUnitTypes();
	for (std::set<UnitType>::iterator i = types.begin(); i != types.end(); ++i) {
		intBuf[index++] = i->getID();
		intBuf[index++] = i->getRace().getID();
		intBuf[index++] = i->whatBuilds().first.getID();
		intBuf[index++] = i->requiredTech().getID();
		intBuf[index++] = i->armorUpgrade().getID();
		intBuf[index++] = i->maxHitPoints();
		intBuf[index++] = i->maxShields();
		intBuf[index++] = i->maxEnergy();
		intBuf[index++] = i->armor();
		intBuf[index++] = i->mineralPrice();
		intBuf[index++] = i->gasPrice();
		intBuf[index++] = i->buildTime();
		intBuf[index++] = i->supplyRequired();
		intBuf[index++] = i->supplyProvided();
		intBuf[index++] = i->spaceRequired();
		intBuf[index++] = i->spaceProvided();
		intBuf[index++] = i->buildScore();
		intBuf[index++] = i->destroyScore();
		intBuf[index++] = i->size().getID();
		intBuf[index++] = i->tileWidth();
		intBuf[index++] = i->tileHeight();
		intBuf[index++] = i->dimensionLeft();
		intBuf[index++] = i->dimensionUp();
		intBuf[index++] = i->dimensionRight();
		intBuf[index++] = i->dimensionDown();
		intBuf[index++] = i->seekRange();
		intBuf[index++] = i->sightRange();
		intBuf[index++] = i->groundWeapon().getID();
		intBuf[index++] = i->maxGroundHits();
		intBuf[index++] = i->airWeapon().getID();
		intBuf[index++] = i->maxAirHits();
		intBuf[index++] = static_cast<int>(i->topSpeed() * fixedScale);
		intBuf[index++] = i->acceleration();
		intBuf[index++] = i->haltDistance();
		intBuf[index++] = i->turnRadius();
		intBuf[index++] = i->canProduce() ? 1 : 0;
		intBuf[index++] = i->canAttack() ? 1 : 0;
		intBuf[index++] = i->canMove() ? 1 : 0;
		intBuf[index++] = i->isFlyer() ? 1 : 0;
		intBuf[index++] = i->regeneratesHP() ? 1 : 0;
		intBuf[index++] = i->isSpellcaster() ? 1 : 0;
		intBuf[index++] = i->isInvincible() ? 1 : 0;
		intBuf[index++] = i->isOrganic() ? 1 : 0;
		intBuf[index++] = i->isMechanical() ? 1 : 0;
		intBuf[index++] = i->isRobotic() ? 1 : 0;
		intBuf[index++] = i->isDetector() ? 1 : 0;
		intBuf[index++] = i->isResourceContainer() ? 1 : 0;
		intBuf[index++] = i->isRefinery() ? 1 : 0;
		intBuf[index++] = i->isWorker() ? 1 : 0;
		intBuf[index++] = i->requiresPsi() ? 1 : 0;
		intBuf[index++] = i->requiresCreep() ? 1 : 0;
		intBuf[index++] = i->isBurrowable() ? 1 : 0;
		intBuf[index++] = i->isCloakable() ? 1 : 0;
		intBuf[index++] = i->isBuilding() ? 1 : 0;
		intBuf[index++] = i->isAddon() ? 1 : 0;
		intBuf[index++] = i->isFlyingBuilding() ? 1 : 0;
		intBuf[index++] = i->isSpell() ? 1 : 0;

		// cloakingTech
		// abilities
		// upgrades
		// isMineralField
		// isTwoUnitsInOneEgg
		// isNeutral
		// isHero
		// isPowerup
		// isBeacon
		// isFlagBeacon
		// isSpecialBuilding
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

JNIEXPORT jstring JNICALL Java_com_harbinger_jbw_Broodwar_getUnitTypeName(JNIEnv* env, jobject jObj, jint unitTypeID)
{
	return env->NewStringUTF(unitTypeMap[unitTypeID].getName().c_str());
}

JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getRequiredUnits(JNIEnv* env, jobject jObj, jint unitTypeID)
{
	int index = 0;
	std::map<UnitType, int> requiredUnits = unitTypeMap[unitTypeID].requiredUnits();
	for (std::map<UnitType, int>::iterator i = requiredUnits.begin(); i != requiredUnits.end(); ++i) {
		intBuf[index++] = i->first.getID();
		intBuf[index++] = i->second;
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getRaceTypes(JNIEnv* env, jobject jObj)
{
	int index = 0;

	std::set<Race> types = Races::allRaces();
	for (std::set<Race>::iterator i = types.begin(); i != types.end(); ++i)
	{
		intBuf[index++] = i->getID();
		intBuf[index++] = i->getWorker().getID();
		intBuf[index++] = i->getCenter().getID();
		intBuf[index++] = i->getRefinery().getID();
		intBuf[index++] = i->getTransport().getID();
		intBuf[index++] = i->getSupplyProvider().getID();
	}

	jintArray result =env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

JNIEXPORT jstring JNICALL Java_com_harbinger_jbw_Broodwar_getRaceTypeName(JNIEnv* env, jobject jObj, jint typeID)
{
	return env->NewStringUTF(raceTypeMap[typeID].getName().c_str());
}

JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getTechTypes(JNIEnv* env, jobject jObj)
{
	int index = 0;

	std::set<TechType> techTypes = TechTypes::allTechTypes();
	for (std::set<TechType>::iterator i = techTypes.begin(); i != techTypes.end(); ++i) {
		intBuf[index++] = i->getID();
		intBuf[index++] = i->getRace().getID();
		intBuf[index++] = i->mineralPrice();
		intBuf[index++] = i->gasPrice();
		intBuf[index++] = i->researchTime();
		intBuf[index++] = i->energyUsed();
		intBuf[index++] = i->whatResearches().getID();
		intBuf[index++] = i->getWeapon().getID();
		intBuf[index++] = i->targetsUnit() ? 1 : 0;
		intBuf[index++] = i->targetsPosition() ? 1 : 0;

		// whatUses
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

JNIEXPORT jstring JNICALL Java_com_harbinger_jbw_Broodwar_getTechTypeName(JNIEnv* env, jobject jObj, jint techID)
{
	return env->NewStringUTF(techTypeMap[techID].getName().c_str());
}

JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getUpgradeTypes(JNIEnv* env, jobject jObj)
{
	int index = 0;

	std::set<UpgradeType> upgradeTypes = UpgradeTypes::allUpgradeTypes();
	for (std::set<UpgradeType>::iterator i = upgradeTypes.begin(); i != upgradeTypes.end(); ++i) {
		intBuf[index++] = i->getID();
		intBuf[index++] = i->getRace().getID();
		intBuf[index++] = i->mineralPrice();
		intBuf[index++] = i->mineralPriceFactor();
		intBuf[index++] = i->gasPrice();
		intBuf[index++] = i->gasPriceFactor(); 
		intBuf[index++] = i->upgradeTime();
		intBuf[index++] = i->upgradeTimeFactor();
		intBuf[index++] = i->maxRepeats();
		intBuf[index++] = i->whatUpgrades().getID();
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

JNIEXPORT jstring JNICALL Java_com_harbinger_jbw_Broodwar_getUpgradeTypeName(JNIEnv* env, jobject jObj, jint upgradeID)
{
	return env->NewStringUTF(upgradeTypeMap[upgradeID].getName().c_str());
}

JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getWeaponTypes(JNIEnv* env, jobject jObj)
{
	int index = 0;

	std::set<WeaponType> weaponTypes = WeaponTypes::allWeaponTypes();
	for (std::set<WeaponType>::iterator i = weaponTypes.begin(); i != weaponTypes.end(); ++i) {
		intBuf[index++] = i->getID();
		intBuf[index++] = i->getTech().getID();
		intBuf[index++] = i->whatUses().getID();
		intBuf[index++] = i->damageAmount();
		intBuf[index++] = i->damageBonus();
		intBuf[index++] = i->damageCooldown();
		intBuf[index++] = i->damageFactor();
		intBuf[index++] = i->upgradeType().getID();
		intBuf[index++] = i->damageType().getID();
		intBuf[index++] = i->explosionType().getID();
		intBuf[index++] = i->minRange();
		intBuf[index++] = i->maxRange();
		intBuf[index++] = i->innerSplashRadius();
		intBuf[index++] = i->medianSplashRadius();
		intBuf[index++] = i->outerSplashRadius();
		intBuf[index++] = i->targetsAir() ? 1 : 0;
		intBuf[index++] = i->targetsGround() ? 1 : 0;
		intBuf[index++] = i->targetsMechanical() ? 1 : 0;
		intBuf[index++] = i->targetsOrganic() ? 1 : 0;
		intBuf[index++] = i->targetsNonBuilding() ? 1 : 0;
		intBuf[index++] = i->targetsNonRobotic() ? 1 : 0;
		intBuf[index++] = i->targetsTerrain() ? 1 : 0;
		intBuf[index++] = i->targetsOrgOrMech() ? 1 : 0;
		intBuf[index++] = i->targetsOwn() ? 1 : 0;
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

JNIEXPORT jstring JNICALL Java_com_harbinger_jbw_Broodwar_getWeaponTypeName(JNIEnv* env, jobject jObj, jint weaponID)
{
	return env->NewStringUTF(weaponTypeMap[weaponID].getName().c_str());
}

JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getUnitSizeTypes(JNIEnv* env, jobject jObj)
{
	int index = 0;

	std::set<UnitSizeType> unitSizeTypes = UnitSizeTypes::allUnitSizeTypes();
	for (std::set<UnitSizeType>::iterator i = unitSizeTypes.begin(); i != unitSizeTypes.end(); ++i) {
		intBuf[index++] = i->getID();
	}

	jintArray result =env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

JNIEXPORT jstring JNICALL Java_com_harbinger_jbw_Broodwar_getUnitSizeTypeName(JNIEnv* env, jobject jObj, jint sizeID)
{
	return env->NewStringUTF(unitSizeTypeMap[sizeID].getName().c_str());
}

JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getBulletTypes(JNIEnv* env, jobject jObj)
{
	int index = 0;

	std::set<BulletType> bulletTypes = BulletTypes::allBulletTypes();
	for (std::set<BulletType>::iterator i = bulletTypes.begin(); i != bulletTypes.end(); ++i) {
		intBuf[index++] = i->getID();
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

JNIEXPORT jstring JNICALL Java_com_harbinger_jbw_Broodwar_getBulletTypeName(JNIEnv* env, jobject jObj, jint bulletID)
{
	return env->NewStringUTF(bulletTypeMap[bulletID].getName().c_str());
}

JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getDamageTypes(JNIEnv* env, jobject jObj)
{
	int index = 0;

	std::set<DamageType> damageTypes = DamageTypes::allDamageTypes();
	for (std::set<DamageType>::iterator i = damageTypes.begin(); i != damageTypes.end(); ++i) {
		intBuf[index++] = i->getID();
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

JNIEXPORT jstring JNICALL Java_com_harbinger_jbw_Broodwar_getDamageTypeName(JNIEnv* env, jobject jObj, jint damageID)
{
	return env->NewStringUTF(damageTypeMap[damageID].getName().c_str());
}

JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getExplosionTypes(JNIEnv* env, jobject jObj)
{
	int index = 0;

	std::set<ExplosionType> explosionTypes = ExplosionTypes::allExplosionTypes();
	for (std::set<ExplosionType>::iterator i = explosionTypes.begin(); i != explosionTypes.end(); ++i) {
		intBuf[index++] = i->getID();
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

JNIEXPORT jstring JNICALL Java_com_harbinger_jbw_Broodwar_getExplosionTypeName(JNIEnv* env, jobject jObj, jint explosionID)
{
	return env->NewStringUTF(explosionTypeMap[explosionID].getName().c_str());
}

JNIEXPORT jstring JNICALL Java_com_harbinger_jbw_Broodwar_getUnitCommandTypeName(JNIEnv* env, jobject jObj, jint unitCommandID)
{
	return env->NewStringUTF(unitCommandTypeMap[unitCommandID].getName().c_str());
}

JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getUnitCommandTypes(JNIEnv* env, jobject jObj)
{
	int index = 0;

	std::set<UnitCommandType> unitCommandTypes = UnitCommandTypes::allUnitCommandTypes();
	for (std::set<UnitCommandType>::iterator i = unitCommandTypes.begin(); i != unitCommandTypes.end(); ++i) {
		intBuf[index++] = i->getID();
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

JNIEXPORT jstring JNICALL Java_com_harbinger_jbw_Broodwar_getOrderTypeName(JNIEnv* env, jobject jObj, jint unitCommandID)
{
	return env->NewStringUTF(orderTypeMap[unitCommandID].getName().c_str());
}

JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getOrderTypes(JNIEnv* env, jobject jObj)
{
	int index = 0;

	std::set<Order> orders = Orders::allOrders();
	for (std::set<Order>::iterator i = orders.begin(); i != orders.end(); ++i) {
		intBuf[index++] = i->getID();
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

/**
* Returns the list of active units in the game.
*
* Each unit takes up a fixed number of integer values. Currently: 123
*/
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getAllUnitsData(JNIEnv* env, jobject jObj)
{
	int index = 0;

	std::set<Unit*> units = Broodwar->getAllUnits();
	for (std::set<Unit*>::iterator i = units.begin(); i != units.end(); ++i) {
		intBuf[index++] = (*i)->getID();
		intBuf[index++] = (*i)->getReplayID();
		intBuf[index++] = (*i)->getPlayer()->getID();
		intBuf[index++] = (*i)->getType().getID();
		intBuf[index++] = (*i)->getPosition().x();
		intBuf[index++] = (*i)->getPosition().y();
		intBuf[index++] = (*i)->getTilePosition().x();
		intBuf[index++] = (*i)->getTilePosition().y();
		intBuf[index++] = static_cast<int>(TO_DEGREES * (*i)->getAngle());
		intBuf[index++] = static_cast<int>(fixedScale * (*i)->getVelocityX());
		intBuf[index++] = static_cast<int>(fixedScale * (*i)->getVelocityY());
		intBuf[index++] = (*i)->getHitPoints();
		intBuf[index++] = (*i)->getShields();
		intBuf[index++] = (*i)->getEnergy();
		intBuf[index++] = (*i)->getResources();
		intBuf[index++] = (*i)->getResourceGroup();
		intBuf[index++] = (*i)->getLastCommandFrame();
		intBuf[index++] = (*i)->getLastCommand().getType().getID();
		// getLastAttackingPlayer doesn't work as documented, have to check for "None" player
		intBuf[index++] = ((*i)->getLastAttackingPlayer() != NULL
			&& (*i)->getLastAttackingPlayer()->getType() != PlayerTypes::None)
			? (*i)->getLastAttackingPlayer()->getID() : -1;
		intBuf[index++] = (*i)->getInitialType().getID();
		intBuf[index++] = (*i)->getInitialPosition().x();
		intBuf[index++] = (*i)->getInitialPosition().y();
		intBuf[index++] = (*i)->getInitialTilePosition().x();
		intBuf[index++] = (*i)->getInitialTilePosition().y();
		intBuf[index++] = (*i)->getInitialHitPoints();
		intBuf[index++] = (*i)->getInitialResources();
		intBuf[index++] = (*i)->getKillCount();
		intBuf[index++] = (*i)->getAcidSporeCount();
		intBuf[index++] = (*i)->getInterceptorCount();
		intBuf[index++] = (*i)->getScarabCount();
		intBuf[index++] = (*i)->getSpiderMineCount();
		intBuf[index++] = (*i)->getGroundWeaponCooldown();
		intBuf[index++] = (*i)->getAirWeaponCooldown();
		intBuf[index++] = (*i)->getSpellCooldown();
		intBuf[index++] = (*i)->getDefenseMatrixPoints();
		intBuf[index++] = (*i)->getDefenseMatrixTimer();
		intBuf[index++] = (*i)->getEnsnareTimer();
		intBuf[index++] = (*i)->getIrradiateTimer();
		intBuf[index++] = (*i)->getLockdownTimer();
		intBuf[index++] = (*i)->getMaelstromTimer();
		intBuf[index++] = (*i)->getOrderTimer();
		intBuf[index++] = (*i)->getPlagueTimer();
		intBuf[index++] = (*i)->getRemoveTimer();
		intBuf[index++] = (*i)->getStasisTimer();
		intBuf[index++] = (*i)->getStimTimer();
		intBuf[index++] = (*i)->getBuildType().getID();
		intBuf[index++] = (*i)->getTrainingQueue().size();
		intBuf[index++] = (*i)->getTech().getID();
		intBuf[index++] = (*i)->getUpgrade().getID();
		intBuf[index++] = (*i)->getRemainingBuildTime();
		intBuf[index++] = (*i)->getRemainingTrainTime();
		intBuf[index++] = (*i)->getRemainingResearchTime();
		intBuf[index++] = (*i)->getRemainingUpgradeTime();
		intBuf[index++] = ((*i)->getBuildUnit() != NULL) ? (*i)->getBuildUnit()->getID() : -1;
		intBuf[index++] = ((*i)->getTarget() != NULL) ? (*i)->getTarget()->getID() : -1;
		intBuf[index++] = (*i)->getTargetPosition().x();
		intBuf[index++] = (*i)->getTargetPosition().y();
		intBuf[index++] = (*i)->getOrder().getID();
		intBuf[index++] = ((*i)->getOrderTarget() != NULL) ? (*i)->getOrderTarget()->getID() : -1;
		intBuf[index++] = (*i)->getSecondaryOrder().getID();
		intBuf[index++] = (*i)->getRallyPosition().x();
		intBuf[index++] = (*i)->getRallyPosition().y();
		intBuf[index++] = ((*i)->getRallyUnit() != NULL) ? (*i)->getRallyUnit()->getID() : -1;
		intBuf[index++] = ((*i)->getAddon() != NULL) ? (*i)->getAddon()->getID() : -1;
		intBuf[index++] = ((*i)->getNydusExit() != NULL) ? (*i)->getNydusExit()->getID() : -1;
		intBuf[index++] = ((*i)->getTransport() != NULL) ? (*i)->getTransport()->getID() : -1;
		intBuf[index++] = (*i)->getLoadedUnits().size(); // see separate getLoadedUnits method
		intBuf[index++] = ((*i)->getCarrier() != NULL) ? (*i)->getCarrier()->getID() : -1;
		// see getInterceptorCount and separate getInterceptors method
		intBuf[index++] = ((*i)->getHatchery() != NULL) ? (*i)->getHatchery()->getID() : -1;
		intBuf[index++] = (*i)->getLarva().size(); // see separate getLarva method
		intBuf[index++] = ((*i)->getPowerUp() != NULL) ? (*i)->getPowerUp()->getID() : -1;
		intBuf[index++] = (*i)->exists() ? 1 : 0;
		intBuf[index++] = (*i)->hasNuke() ? 1 : 0;
		intBuf[index++] = (*i)->isAccelerating() ? 1 : 0;
		intBuf[index++] = (*i)->isAttacking() ? 1 : 0;
		intBuf[index++] = (*i)->isAttackFrame() ? 1 : 0;
		intBuf[index++] = (*i)->isBeingConstructed() ? 1 : 0;
		intBuf[index++] = (*i)->isBeingGathered() ? 1 : 0;
		intBuf[index++] = (*i)->isBeingHealed() ? 1 : 0;
		intBuf[index++] = (*i)->isBlind() ? 1 : 0;
		intBuf[index++] = (*i)->isBraking() ? 1 : 0;
		intBuf[index++] = (*i)->isBurrowed() ? 1 : 0;
		intBuf[index++] = (*i)->isCarryingGas() ? 1 : 0;
		intBuf[index++] = (*i)->isCarryingMinerals() ? 1 : 0;
		intBuf[index++] = (*i)->isCloaked() ? 1 : 0;
		intBuf[index++] = (*i)->isCompleted() ? 1 : 0;
		intBuf[index++] = (*i)->isConstructing() ? 1 : 0;
		intBuf[index++] = (*i)->isDefenseMatrixed() ? 1 : 0;
		intBuf[index++] = (*i)->isDetected() ? 1 : 0;
		intBuf[index++] = (*i)->isEnsnared() ? 1 : 0;
		intBuf[index++] = (*i)->isFollowing() ? 1 : 0;
		intBuf[index++] = (*i)->isGatheringGas() ? 1 : 0;
		intBuf[index++] = (*i)->isGatheringMinerals() ? 1 : 0;
		intBuf[index++] = (*i)->isHallucination() ? 1 : 0;
		intBuf[index++] = (*i)->isHoldingPosition() ? 1 : 0;
		intBuf[index++] = (*i)->isIdle() ? 1 : 0;
		intBuf[index++] = (*i)->isInterruptible() ? 1 : 0;
		intBuf[index++] = (*i)->isInvincible() ? 1 : 0;
		intBuf[index++] = (*i)->isIrradiated() ? 1 : 0;
		intBuf[index++] = (*i)->isLifted() ? 1 : 0;
		intBuf[index++] = (*i)->isLoaded() ? 1 : 0;
		intBuf[index++] = (*i)->isLockedDown() ? 1 : 0;
		intBuf[index++] = (*i)->isMaelstrommed() ? 1 : 0;
		intBuf[index++] = (*i)->isMorphing() ? 1 : 0;
		intBuf[index++] = (*i)->isMoving() ? 1 : 0;
		intBuf[index++] = (*i)->isParasited() ? 1 : 0;
		intBuf[index++] = (*i)->isPatrolling() ? 1 : 0;
		intBuf[index++] = (*i)->isPlagued() ? 1 : 0;
		intBuf[index++] = (*i)->isRepairing() ? 1 : 0;
		intBuf[index++] = (*i)->isSelected() ? 1 : 0;
		intBuf[index++] = (*i)->isSieged() ? 1 : 0;
		intBuf[index++] = (*i)->isStartingAttack() ? 1 : 0;
		intBuf[index++] = (*i)->isStasised() ? 1 : 0;
		intBuf[index++] = (*i)->isStimmed() ? 1 : 0;
		intBuf[index++] = (*i)->isStuck() ? 1 : 0;
		intBuf[index++] = (*i)->isTraining() ? 1 : 0;
		intBuf[index++] = (*i)->isUnderAttack() ? 1 : 0;
		intBuf[index++] = (*i)->isUnderDarkSwarm() ? 1 : 0;
		intBuf[index++] = (*i)->isUnderDisruptionWeb() ? 1 : 0;
		intBuf[index++] = (*i)->isUnderStorm() ? 1 : 0;
		intBuf[index++] = (*i)->isUnpowered() ? 1 : 0;
		intBuf[index++] = (*i)->isUpgrading() ? 1 : 0;
		intBuf[index++] = (*i)->isVisible() ? 1 : 0;
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getLoadedUnits(JNIEnv* env, jobject, jint unitID)
{
	int index = 0;

	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		std::set<Unit*> loaded = unit->getLoadedUnits();
		for (std::set<Unit*>::const_iterator it = loaded.begin(); it != loaded.end(); it++){
			intBuf[index++] = (*it)->getID();
		}
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getInterceptors(JNIEnv* env, jobject, jint unitID)
{
	int index = 0;

	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		std::set<Unit*> loaded = unit->getInterceptors();
		for (std::set<Unit*>::const_iterator it = loaded.begin(); it != loaded.end(); it++){
			intBuf[index++] = (*it)->getID();
		}
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getLarva(JNIEnv* env, jobject, jint unitID)
{
	int index = 0;

	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		std::set<Unit*> loaded = unit->getLarva();
		for (std::set<Unit*>::const_iterator it = loaded.begin(); it != loaded.end(); it++){
			intBuf[index++] = (*it)->getID();
		}
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_isVisibleToPlayer(JNIEnv* env, jobject jObj, jint unitID, jint playerID)
{
	Unit* u = Broodwar->getUnit(unitID);
	Player* p = Broodwar->getPlayer(playerID);
	if (u != NULL && p != NULL)
	{
		return u->isVisible(p);
	}
	return false;
}

/*****************************************************************************************************************/
// Map queries
/*****************************************************************************************************************/

JNIEXPORT jint JNICALL Java_com_harbinger_jbw_Broodwar_getMapWidth(JNIEnv* env, jobject jObj)
{
	return Broodwar->mapWidth();
}

JNIEXPORT jint JNICALL Java_com_harbinger_jbw_Broodwar_getMapHeight(JNIEnv* env, jobject jObj)
{
	return Broodwar->mapHeight();
}

JNIEXPORT jstring JNICALL Java_com_harbinger_jbw_Broodwar_getMapFileName(JNIEnv* env, jobject jObj)
{
	return env->NewStringUTF(Broodwar->mapFileName().c_str());
}

JNIEXPORT jbyteArray JNICALL Java_com_harbinger_jbw_Broodwar_getMapName(JNIEnv* env, jobject jObj)
{
	// NewStringUTF causes issues with unusual characters like Korean symbols
	std::string str = Broodwar->mapName();
	jbyteArray jbArray = env->NewByteArray(str.length());
	env->SetByteArrayRegion(jbArray, 0, str.length(), (jbyte*)str.c_str());
	return jbArray;
}

JNIEXPORT jstring JNICALL Java_com_harbinger_jbw_Broodwar_getMapHash(JNIEnv* env, jobject jObj)
{
	return env->NewStringUTF(Broodwar->mapHash().c_str());
}

JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getMapDepth(JNIEnv* env, jobject jObj)
{
	int index = 0;
	int width = Broodwar->mapWidth();
	int height = Broodwar->mapHeight();

	for (int ty = 0; ty < height; ++ty) {
		for (int tx = 0; tx < width; ++tx) {
			intBuf[index++] = Broodwar->getGroundHeight(tx, ty);
		}
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

// Returns the regionId for each map tile
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getRegionMap(JNIEnv* env, jobject jObj)
{
	int index = 0;
	int width = Broodwar->mapWidth();
	int height = Broodwar->mapHeight();

	for (int ty=0; ty<height; ty++) {
		for (int tx=0; tx<width; tx++) {
			BWTA::Region* region = BWTA::getRegion(tx, ty);
			intBuf[index++] = regionMap[region];
		}
	}

	jintArray result =env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getWalkableData(JNIEnv* env, jobject jObj)
{
	// Note: walk tiles are 8x8 pixels, build tiles are 32x32 pixels
	int index = 0;	
	int width = 4 * Broodwar->mapWidth();
	int height = 4 * Broodwar->mapHeight();

	for (int ty = 0; ty < height; ++ty) {
		for (int tx = 0; tx < width; ++tx) {
			intBuf[index++] = Broodwar->isWalkable(tx, ty) ? 1 : 0;
		}
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getBuildableData(JNIEnv* env, jobject jObj)
{
	int index = 0;
	int width = Broodwar->mapWidth();
	int height = Broodwar->mapHeight();

	for (int ty = 0; ty < height; ++ty) {
		for (int tx = 0; tx < width; ++tx) {
			intBuf[index++] = Broodwar->isBuildable(tx, ty) ? 1 : 0;
		}
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_analyzeTerrain(JNIEnv* env, jobject jObj)
{
	regionMap.clear();
	BWTA::readMap();
	BWTA::analyze();

	// assign IDs to regions
	int regionID = 1;
	std::set<BWTA::Region*> regions = BWTA::getRegions();
	for (std::set<BWTA::Region*>::iterator i = regions.begin(); i != regions.end(); ++i) {
		regionMap[(*i)] = regionID++;
	}
}

JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getBaseLocations(JNIEnv* env, jobject jObj)
{
	int index = 0;

	std::set<BWTA::BaseLocation*> baseLocation = BWTA::getBaseLocations();
	for (std::set<BWTA::BaseLocation*>::iterator i = baseLocation.begin(); i != baseLocation.end(); ++i) {
		intBuf[index++] = (*i)->getPosition().x();
		intBuf[index++] = (*i)->getPosition().y();
		intBuf[index++] = (*i)->getTilePosition().x();
		intBuf[index++] = (*i)->getTilePosition().y();
		intBuf[index++] = regionMap[(*i)->getRegion()];
		intBuf[index++] = (*i)->minerals();
		intBuf[index++] = (*i)->gas();
		intBuf[index++] = (*i)->isIsland() ? 1 : 0;
		intBuf[index++] = (*i)->isMineralOnly() ? 1 : 0;
		intBuf[index++] = (*i)->isStartLocation() ? 1 : 0;
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getUnitIdsOnTile(JNIEnv * env, jobject jObj, jint tx, jint ty)
{
	std::set<Unit*> unitsOnTile = Broodwar->getUnitsOnTile(tx, ty);
	int index = 0;
	for (std::set<Unit*>::iterator i = unitsOnTile.begin(); i != unitsOnTile.end(); ++i)
	{
		intBuf[index++] = (*i)->getID();
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

/*****************************************************************************************************************/
// Unit Commands
/*****************************************************************************************************************/

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_attack__III(JNIEnv* env, jobject jObj, jint unitID, jint x, jint y)
{
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		return unit->attack(BWAPI::Position(x, y), false); 
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_attack__II(JNIEnv* env, jobject jObj, jint unitID, jint targetID)
{
	Unit* unit = Broodwar->getUnit(unitID);
	Unit* target = Broodwar->getUnit(targetID);
	if (unit != NULL && target != NULL) {
		return unit->attack(target, false);
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_build(JNIEnv* env, jobject jObj, jint unitID, jint typeID, jint tx, jint ty)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		if (unitTypeMap.count(typeID) > 0) {
			return unit->build(BWAPI::TilePosition(tx, ty), unitTypeMap[typeID]);
		}
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_buildAddon(JNIEnv* env, jobject jObj, jint unitID, jint typeID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		if (unitTypeMap.count(typeID) > 0) {
			return unit->buildAddon(unitTypeMap[typeID]);
		}
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_train(JNIEnv* env, jobject jObj, jint unitID, jint typeID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		if (unitTypeMap.count(typeID) > 0) {
			return unit->train(unitTypeMap[typeID]);
		}
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_morph(JNIEnv* env, jobject jObj, jint unitID, jint typeID)
{
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		if (unitTypeMap.count(typeID) > 0) {
			return unit->morph(unitTypeMap[typeID]);
		}
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_research(JNIEnv* env, jobject jObj, jint unitID, jint techID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		if (techTypeMap.count(techID) > 0) {
			return unit->research(techTypeMap[techID]);
		}
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_upgrade(JNIEnv* env, jobject jObj, jint unitID, jint upgradeID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		if (upgradeTypeMap.count(upgradeID) > 0) {
			return unit->upgrade(upgradeTypeMap[upgradeID]);
		}
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_setRallyPoint__III(JNIEnv* env, jobject jObj, jint unitID, jint x, jint y)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		return unit->setRallyPoint(BWAPI::Position(x, y));
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_setRallyPoint__II(JNIEnv* env, jobject jObj, jint unitID, jint targetID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	Unit* target = Broodwar->getUnit(targetID);
	if (unit != NULL && target != NULL) {
		return unit->setRallyPoint(target);
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_move(JNIEnv* env, jobject jObj, jint unitID, jint x, jint y)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		return unit->move(BWAPI::Position(x, y));
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_patrol(JNIEnv* env, jobject jObj, jint unitID, jint x, jint y)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		return unit->patrol(BWAPI::Position(x, y));
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_holdPosition(JNIEnv* env, jobject jObj, jint unitID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		return unit->holdPosition();
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_stop(JNIEnv* env, jobject jObj, jint unitID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		return unit->stop();
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_follow(JNIEnv* env, jobject jObj, jint unitID, jint targetID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	Unit* target = Broodwar->getUnit(targetID);
	if (unit != NULL && target != NULL) {
		return unit->follow(target);
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_gather(JNIEnv* env, jobject jObj, jint unitID, jint targetID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	Unit* target = Broodwar->getUnit(targetID);
	if (unit != NULL && target != NULL) {
		return unit->gather(target);
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_returnCargo(JNIEnv* env, jobject jObj, jint unitID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		return unit->returnCargo();
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_repair(JNIEnv* env, jobject jObj, jint unitID, jint targetID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	Unit* target = Broodwar->getUnit(targetID);
	if (unit != NULL && target != NULL) {
		return unit->repair(target);
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_burrow(JNIEnv* env, jobject jObj, jint unitID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		return unit->burrow();
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_unburrow(JNIEnv* env, jobject jObj, jint unitID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		return unit->unburrow();
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_cloak(JNIEnv* env, jobject jObj, jint unitID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		return unit->cloak();
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_decloak(JNIEnv* env, jobject jObj, jint unitID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		return unit->decloak();
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_siege(JNIEnv* env, jobject jObj, jint unitID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		return unit->siege();
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_unsiege(JNIEnv* env, jobject jObj, jint unitID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		return unit->unsiege();
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_lift(JNIEnv* env, jobject jObj, jint unitID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		return unit->lift();
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_land(JNIEnv* env, jobject jObj, jint unitID, jint tx, jint ty)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		return unit->land(BWAPI::TilePosition(tx, ty));
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_load(JNIEnv* env, jobject jObj, jint unitID, jint targetID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	Unit* target = Broodwar->getUnit(targetID);
	if (unit != NULL && target != NULL) {
		return unit->load(target);
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_unload(JNIEnv* env, jobject jObj, jint unitID, jint targetID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	Unit* target = Broodwar->getUnit(targetID);
	if (unit != NULL && target != NULL) {
		return unit->unload(target);
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_unloadAll__I(JNIEnv* env, jobject jObj, jint unitID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		return unit->unloadAll();
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_unloadAll__III(JNIEnv* env, jobject jObj, jint unitID, jint x, jint y)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		return unit->unloadAll(BWAPI::Position(x, y));
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_rightClick__III(JNIEnv* env, jobject jObj, jint unitID, jint x, jint y)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		return unit->rightClick(BWAPI::Position(x, y));
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_rightClick__II(JNIEnv* env, jobject jObj, jint unitID, jint targetID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	Unit* target = Broodwar->getUnit(targetID);
	if (unit != NULL && target != NULL) {
		return unit->rightClick(target);
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_haltConstruction(JNIEnv* env, jobject jObj, jint unitID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		return unit->haltConstruction();
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_cancelConstruction(JNIEnv* env, jobject jObj, jint unitID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		return unit->cancelConstruction();
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_cancelAddon(JNIEnv* env, jobject jObj, jint unitID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		return unit->cancelAddon();
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_cancelTrain(JNIEnv* env, jobject jObj, jint unitID, jint slot)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		return unit->cancelTrain(slot);
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_cancelMorph(JNIEnv* env, jobject jObj, jint unitID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		return unit->cancelMorph();
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_cancelResearch(JNIEnv* env, jobject jObj, jint unitID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		return unit->cancelResearch();
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_cancelUpgrade(JNIEnv* env, jobject jObj, jint unitID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		return unit->cancelUpgrade();
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_useTech__II(JNIEnv* env, jobject jObj, jint unitID, jint techID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		if (techTypeMap.count(techID) > 0) {
			return unit->useTech(techTypeMap[techID]);
		}
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_useTech__IIII(JNIEnv* env, jobject jObj, jint unitID, jint techID, jint x, jint y)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		if (techTypeMap.count(techID) > 0) {
			return unit->useTech(techTypeMap[techID], BWAPI::Position(x, y));
		}			
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Unit_useTech__III(JNIEnv* env, jobject jObj, jint unitID, jint techID, jint targetID)
{ 
	Unit* unit = Broodwar->getUnit(unitID);
	Unit* target = Broodwar->getUnit(targetID);
	if (unit != NULL && target != NULL) {
		if (techTypeMap.count(techID) > 0) {
			return unit->useTech(techTypeMap[techID], target);
		}
	}
	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_placeCOP(JNIEnv* env, jobject jObj, jint unitID, jint tx, jint ty)
{
	Unit* unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		return unit->placeCOP(BWAPI::TilePosition(tx, ty));
	}
	return JNI_FALSE;
}

/*****************************************************************************************************************/
// Extended functions
/*****************************************************************************************************************/

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_isVisible(JNIEnv* env, jobject jObj, jint tileX, jint tileY)
{
	return Broodwar->isVisible(tileX, tileY);
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_isExplored(JNIEnv* env, jobject jObj, jint tileX, jint tileY)
{
	return Broodwar->isExplored(tileX, tileY);
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_isBuildable(JNIEnv *, jobject, jint tx, jint ty, jboolean includeBuildings){
	bool checkBuildings = false;
	if (includeBuildings)
	{
		checkBuildings = true;
	}
	return Broodwar->isBuildable(tx, ty, checkBuildings);
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_hasCreep(JNIEnv* env, jobject jObj, jint tx, jint ty)
{
	return Broodwar->hasCreep(tx, ty);
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_hasPower__II(JNIEnv* env, jobject jObj, jint tileX, jint tileY)
{
	return Broodwar->hasPower(tileX, tileY);
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_hasPower__III(JNIEnv* env, jobject jObj, jint tileX, jint tileY, jint unitID)
{
	Unit* unit = Broodwar->getUnit(unitID);

	if (unit != NULL) {
		return Broodwar->hasPower(tileX, tileY, unit->getType());
	}

	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_hasPower__IIII(JNIEnv* env, jobject jObj, jint tileX, jint tileY, jint tileWidth, jint tileHeight)
{
	return Broodwar->hasPower(tileX, tileY, tileWidth, tileHeight);
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_hasPower__IIIII(JNIEnv* env, jobject jObj, jint tileX, jint tileY, jint tileWidth, jint tileHeight, jint unitID)
{
	Unit* unit = Broodwar->getUnit(unitID);

	if (unit != NULL) {
		return Broodwar->hasPower(tileX, tileY, tileWidth, tileHeight);
	}

	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_hasPowerPrecise(JNIEnv* env, jobject jObj, jint x, jint y)
{
	return Broodwar->hasPowerPrecise(x, y);
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_hasPath__IIII(JNIEnv* env, jobject jObj, jint fromX, jint fromY, jint toX, jint toY)
{
	return Broodwar->hasPath(BWAPI::Position(fromX, fromY), BWAPI::Position(toX, toY));
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_hasPath__II(JNIEnv* env, jobject jObj, jint unitID, jint targetID)
{
	Unit* unit = Broodwar->getUnit(unitID);
	Unit* target = Broodwar->getUnit(targetID);

	if (unit != NULL && target != NULL) {
		return unit->hasPath(target);
	}

	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_hasPath__III(JNIEnv* env, jobject jObj, jint unitID, jint toX, jint toY)
{
	Unit* unit = Broodwar->getUnit(unitID);

	if (unit != NULL) {
		unit->hasPath(Position(toX, toY));
	}

	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_canBuildHere__IIIZ(JNIEnv* env, jobject jObj, jint tileX, jint tileY, jint unitTypeID, jboolean checkExplored)
{
	if (unitTypeMap.count(unitTypeID) > 0) {
		BWAPI::UnitType unitType = unitTypeMap[unitTypeID];
		return Broodwar->canBuildHere(NULL, TilePosition(tileX, tileY), unitType, checkExplored != JNI_FALSE);
	}

	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_canBuildHere__IIIIZ(JNIEnv* env, jobject jObj, jint unitID, jint tileX, jint tileY, jint unitTypeID, jboolean checkExplored)
{
	if (unitTypeMap.count(unitTypeID) > 0) {
		BWAPI::Unit* unit = Broodwar->getUnit(unitID);
		BWAPI::UnitType unitType = unitTypeMap[unitTypeID];
		return Broodwar->canBuildHere(unit, TilePosition(tileX, tileY), unitType, checkExplored != JNI_FALSE);
	}

	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_canMake__I(JNIEnv* env, jobject jObj, jint unitTypeID)
{
	if (unitTypeMap.count(unitTypeID) > 0) {
		BWAPI::UnitType unitType = unitTypeMap[unitTypeID];
		return Broodwar->canMake(NULL, unitType);
	}

	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_canMake__II(JNIEnv* env, jobject jObj, jint unitID, jint unitTypeID)
{
	if (unitTypeMap.count(unitTypeID) > 0) {
		BWAPI::Unit* unit = Broodwar->getUnit(unitID);
		BWAPI::UnitType unitType = unitTypeMap[unitTypeID];
		return Broodwar->canMake(unit, unitType);
	}

	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_canResearch__I(JNIEnv* env, jobject jObj, jint techTypeID)
{
	if (techTypeMap.count(techTypeID) > 0) {
		BWAPI::TechType techType = techTypeMap[techTypeID];
		return Broodwar->canResearch(NULL, techType);
	}

	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_canResearch__II(JNIEnv* env, jobject jObj, jint unitID, jint techTypeID)
{
	if (techTypeMap.count(techTypeID) > 0) {
		BWAPI::Unit* unit = Broodwar->getUnit(unitID);
		BWAPI::TechType techType = unitTypeMap[techTypeID];
		return Broodwar->canResearch(unit, techType);
	}

	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_canUpgrade__I(JNIEnv* env, jobject jObj, jint upgradeTypeID)
{
	if (upgradeTypeMap.count(upgradeTypeID) > 0) {
		BWAPI::UpgradeType upgradeType = upgradeTypeMap[upgradeTypeID];
		return Broodwar->canUpgrade(NULL, upgradeType);
	}

	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_canUpgrade__II(JNIEnv* env, jobject jObj, jint unitID, jint upgradeTypeID)
{
	if (upgradeTypeMap.count(upgradeTypeID) > 0) {
		BWAPI::Unit* unit = Broodwar->getUnit(unitID);
		BWAPI::UpgradeType upgradeType = upgradeTypeMap[upgradeTypeID];
		return Broodwar->canUpgrade(unit, upgradeType);
	}

	return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_isReplay(JNIEnv *env, jobject jObj)
{
	return Broodwar->isReplay();
}

JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_sendText(JNIEnv* env, jobject jObj, jstring message)
{
	const char* messagechars = env->GetStringUTFChars(message, 0);
	Broodwar->sendText("%s", messagechars);
	env->ReleaseStringUTFChars(message, messagechars);
}

JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_setCommandOptimizationLevel(JNIEnv* env, jobject jObj, jint level)
{
	Broodwar->setCommandOptimizationLevel(level);
}

JNIEXPORT jint JNICALL Java_com_harbinger_jbw_Broodwar_getLastErrorCode(JNIEnv *, jobject){
	return Broodwar->getLastError().getID();
}

JNIEXPORT jint JNICALL Java_com_harbinger_jbw_Broodwar_getRemainingLatencyFrames(JNIEnv *, jobject){

	return Broodwar->getRemainingLatencyFrames();
}

// ************************************************************************************************
// Drawing Commands
// ************************************************************************************************

JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_drawTextMap(JNIEnv* env, jobject jObj, jint x, jint y, jstring msg)
{
	const char* text = env->GetStringUTFChars(msg, 0);
	Broodwar->drawTextMap(x, y, "%s", text);
	env->ReleaseStringUTFChars(msg, text);
}

JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_drawTextScreen(JNIEnv* env, jobject jObj, jint x, jint y, jstring msg)
{
	const char* text = env->GetStringUTFChars(msg, 0);
	Broodwar->drawTextScreen(x, y, "%s", text);
	env->ReleaseStringUTFChars(msg, text);
}

JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_drawLineMap(JNIEnv* env, jobject jObj, jint x1, jint y1, jint x2, jint y2, jint color)
{
	Broodwar->drawLineMap(x1, y1, x2, y2, BWAPI::Color(color));
}

JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_drawLineScreen(JNIEnv* env, jobject jObj, jint x1, jint y1, jint x2, jint y2, jint color)
{
	Broodwar->drawLineScreen(x1, y1, x2, y2, BWAPI::Color(color));
}

JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_drawRectangleMap(JNIEnv* env, jobject jObj, jint x, jint y, jint width, jint height, jint color, jboolean fill)
{
	Broodwar->drawBoxMap(x, y, x + width, y + height, BWAPI::Color(color), fill ? true : false);
}

JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_drawRectangleScreen(JNIEnv* env, jobject jObj, jint x, jint y, jint width, jint height, jint color, jboolean fill)
{
	Broodwar->drawBoxScreen(x, y, x + width, y + height, BWAPI::Color(color), fill ? true : false);
}

JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_drawEllipseMap(JNIEnv* env, jobject jObj, jint x, jint y, jint xRadius, jint yRadius, jint color, jboolean fill)
{
	Broodwar->drawEllipseMap(x, y, xRadius, yRadius, BWAPI::Color(color), fill ? true : false);
}

JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_drawEllipseScreen(JNIEnv* env, jobject jObj, jint x, jint y, jint xRadius, jint yRadius, jint color, jboolean fill)
{
	Broodwar->drawEllipseScreen(x, y, xRadius, yRadius, BWAPI::Color(color), fill ? true : false);
}