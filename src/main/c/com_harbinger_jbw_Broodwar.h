/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_harbinger_jbw_Broodwar */

#ifndef _Included_com_harbinger_jbw_Broodwar
#define _Included_com_harbinger_jbw_Broodwar
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getUnitIdsOnTile
 * Signature: (II)[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getUnitIdsOnTile
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    startClient
 * Signature: (Lcom/harbinger/jbw/Broodwar;)V
 */
JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_startClient
  (JNIEnv *, jobject, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getFrame
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_harbinger_jbw_Broodwar_getFrame
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getRemainingLatencyFrames
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_harbinger_jbw_Broodwar_getRemainingLatencyFrames
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getReplayFrameTotal
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_harbinger_jbw_Broodwar_getReplayFrameTotal
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    isReplay
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_isReplay
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getLastError
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_harbinger_jbw_Broodwar_getLastError
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    leaveGame
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_leaveGame
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    enableUserInput
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_enableUserInput
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    enablePerfectInformation
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_enablePerfectInformation
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    setGameSpeed
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_setGameSpeed
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    setFrameSkip
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_setFrameSkip
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    setCommandOptimizationLevel
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_setCommandOptimizationLevel
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    sendText
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_sendText
  (JNIEnv *, jobject, jstring);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getLoadedUnits
 * Signature: (I)[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getLoadedUnits
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getInterceptors
 * Signature: (I)[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getInterceptors
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getLarva
 * Signature: (I)[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getLarva
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    isVisible
 * Signature: (II)Z
 */
JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_isVisible
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    isVisibleToPlayer
 * Signature: (II)Z
 */
JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_isVisibleToPlayer
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    isExplored
 * Signature: (II)Z
 */
JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_isExplored
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    isBuildable
 * Signature: (IIZ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_isBuildable
  (JNIEnv *, jobject, jint, jint, jboolean);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    canBuildHere
 * Signature: (IIIZ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_canBuildHere__IIIZ
  (JNIEnv *, jobject, jint, jint, jint, jboolean);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    canBuildHere
 * Signature: (IIIIZ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_canBuildHere__IIIIZ
  (JNIEnv *, jobject, jint, jint, jint, jint, jboolean);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    hasCreep
 * Signature: (II)Z
 */
JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_hasCreep
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    hasPower
 * Signature: (III)Z
 */
JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_hasPower__III
  (JNIEnv *, jobject, jint, jint, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    hasPower
 * Signature: (IIIII)Z
 */
JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_hasPower__IIIII
  (JNIEnv *, jobject, jint, jint, jint, jint, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    hasPowerPrecise
 * Signature: (III)Z
 */
JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_hasPowerPrecise
  (JNIEnv *, jobject, jint, jint, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    hasPath
 * Signature: (IIII)Z
 */
JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_hasPath__IIII
  (JNIEnv *, jobject, jint, jint, jint, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    hasPath
 * Signature: (II)Z
 */
JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_hasPath__II
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    hasPath
 * Signature: (III)Z
 */
JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_hasPath__III
  (JNIEnv *, jobject, jint, jint, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    canResearch
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_canResearch__I
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    canMake
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_canMake__I
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    canMake
 * Signature: (II)Z
 */
JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_canMake__II
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    canResearch
 * Signature: (II)Z
 */
JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_canResearch__II
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    canUpgrade
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_canUpgrade__I
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    canUpgrade
 * Signature: (II)Z
 */
JNIEXPORT jboolean JNICALL Java_com_harbinger_jbw_Broodwar_canUpgrade__II
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getPlayersData
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getPlayersData
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getAllUnitsData
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getAllUnitsData
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getPlayerUpdate
 * Signature: (I)[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getPlayerUpdate
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getPlayerName
 * Signature: (I)[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_harbinger_jbw_Broodwar_getPlayerName
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getResearchStatus
 * Signature: (I)[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getResearchStatus
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getUpgradeStatus
 * Signature: (I)[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getUpgradeStatus
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getRaceTypes
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getRaceTypes
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getRaceTypeName
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_harbinger_jbw_Broodwar_getRaceTypeName
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getUnitTypes
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getUnitTypes
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getUnitTypeName
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_harbinger_jbw_Broodwar_getUnitTypeName
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getRequiredUnits
 * Signature: (I)[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getRequiredUnits
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getTechTypes
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getTechTypes
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getTechTypeName
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_harbinger_jbw_Broodwar_getTechTypeName
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getUpgradeTypes
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getUpgradeTypes
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getUpgradeTypeName
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_harbinger_jbw_Broodwar_getUpgradeTypeName
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getWeaponTypes
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getWeaponTypes
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getWeaponTypeName
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_harbinger_jbw_Broodwar_getWeaponTypeName
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getUnitSizeTypes
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getUnitSizeTypes
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getUnitSizeTypeName
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_harbinger_jbw_Broodwar_getUnitSizeTypeName
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getBulletTypes
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getBulletTypes
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getBulletTypeName
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_harbinger_jbw_Broodwar_getBulletTypeName
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getDamageTypes
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getDamageTypes
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getDamageTypeName
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_harbinger_jbw_Broodwar_getDamageTypeName
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getExplosionTypes
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getExplosionTypes
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getExplosionTypeName
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_harbinger_jbw_Broodwar_getExplosionTypeName
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getUnitCommandTypes
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getUnitCommandTypes
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getUnitCommandTypeName
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_harbinger_jbw_Broodwar_getUnitCommandTypeName
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getOrderTypes
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getOrderTypes
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getOrderTypeName
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_harbinger_jbw_Broodwar_getOrderTypeName
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    analyzeTerrain
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_analyzeTerrain
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getMapName
 * Signature: ()[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_harbinger_jbw_Broodwar_getMapName
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getMapFileName
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_harbinger_jbw_Broodwar_getMapFileName
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getMapHash
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_harbinger_jbw_Broodwar_getMapHash
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getMapWidth
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_harbinger_jbw_Broodwar_getMapWidth
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getMapHeight
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_harbinger_jbw_Broodwar_getMapHeight
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getMapDepth
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getMapDepth
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getRegionMap
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getRegionMap
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getWalkableData
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getWalkableData
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getBuildableData
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getBuildableData
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getChokePoints
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getChokePoints
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getRegions
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getRegions
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getPolygon
 * Signature: (I)[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getPolygon
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    getBaseLocations
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Java_com_harbinger_jbw_Broodwar_getBaseLocations
  (JNIEnv *, jobject);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    drawHealth
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_drawHealth
  (JNIEnv *, jobject, jboolean);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    drawTargets
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_drawTargets
  (JNIEnv *, jobject, jboolean);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    drawIDs
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_drawIDs
  (JNIEnv *, jobject, jboolean);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    drawBox
 * Signature: (IIIIIZZ)V
 */
JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_drawBox
  (JNIEnv *, jobject, jint, jint, jint, jint, jint, jboolean, jboolean);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    drawCircle
 * Signature: (IIIIZZ)V
 */
JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_drawCircle
  (JNIEnv *, jobject, jint, jint, jint, jint, jboolean, jboolean);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    drawLine
 * Signature: (IIIIIZ)V
 */
JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_drawLine
  (JNIEnv *, jobject, jint, jint, jint, jint, jint, jboolean);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    drawDot
 * Signature: (IIIZ)V
 */
JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_drawDot
  (JNIEnv *, jobject, jint, jint, jint, jboolean);

/*
 * Class:     com_harbinger_jbw_Broodwar
 * Method:    drawText
 * Signature: (IILjava/lang/String;Z)V
 */
JNIEXPORT void JNICALL Java_com_harbinger_jbw_Broodwar_drawText
  (JNIEnv *, jobject, jint, jint, jstring, jboolean);

#ifdef __cplusplus
}
#endif
#endif
