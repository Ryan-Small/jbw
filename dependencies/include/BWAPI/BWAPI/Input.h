#pragma once
namespace BWAPI
{
  enum MouseButton
  {
    M_LEFT   = 0,
    M_RIGHT  = 1,
    M_MIDDLE = 2,
    M_MAX
  };

  enum Key
  {
    K_LBUTTON             = 0x01,
    K_RBUTTON             = 0x02,
    K_CANCEL              = 0x03,
    K_MBUTTON             = 0x04,
    K_XBUTTON1            = 0x05,
    K_XBUTTON2            = 0x06,
    K_BACK                = 0x08,
    K_TAB                 = 0x09,
    K_CLEAR               = 0x0C,
    K_RETURN              = 0x0D,
    K_SHIFT               = 0x10,
    K_CONTROL             = 0x11,
    K_MENU                = 0x12,
    K_PAUSE               = 0x13,
    K_CAPITAL             = 0x14,
    K_KANA                = 0x15,
    K_HANGEUL             = 0x15,
    K_HANGUL              = 0x15,
    K_JUNJA               = 0x17,
    K_FINAL               = 0x18,
    K_HANJA               = 0x19,
    K_KANJI               = 0x19,
    K_ESCAPE              = 0x1B,
    K_CONVERT             = 0x1C,
    K_NONCONVERT          = 0x1D,
    K_ACCEPT              = 0x1E,
    K_MODECHANGE          = 0x1F,
    K_SPACE               = 0x20,
    K_PRIOR               = 0x21,
    K_NEXT                = 0x22,
    K_END                 = 0x23,
    K_HOME                = 0x24,
    K_LEFT                = 0x25,
    K_UP                  = 0x26,
    K_RIGHT               = 0x27,
    K_DOWN                = 0x28,
    K_SELECT              = 0x29,
    K_PRINT               = 0x2A,
    K_EXECUTE             = 0x2B,
    K_SNAPSHOT            = 0x2C,
    K_INSERT              = 0x2D,
    K_DELETE              = 0x2E,
    K_HELP                = 0x2F,
    K_0                   = 0x30,
    K_1                   = 0x31,
    K_2                   = 0x32,
    K_3                   = 0x33,
    K_4                   = 0x34,
    K_5                   = 0x35,
    K_6                   = 0x36,
    K_7                   = 0x37,
    K_8                   = 0x38,
    K_9                   = 0x39,
    K_A                   = 0x41,
    K_B                   = 0x42,
    K_C                   = 0x43,
    K_D                   = 0x44,
    K_E                   = 0x45,
    K_F                   = 0x46,
    K_G                   = 0x47,
    K_H                   = 0x48,
    K_I                   = 0x49,
    K_J                   = 0x4A,
    K_K                   = 0x4B,
    K_L                   = 0x4C,
    K_M                   = 0x4D,
    K_N                   = 0x4E,
    K_O                   = 0x4F,
    K_P                   = 0x50,
    K_Q                   = 0x51,
    K_R                   = 0x52,
    K_S                   = 0x53,
    K_T                   = 0x54,
    K_U                   = 0x55,
    K_V                   = 0x56,
    K_W                   = 0x57,
    K_X                   = 0x58,
    K_Y                   = 0x59,
    K_Z                   = 0x5A,
    K_LWIN                = 0x5B,
    K_RWIN                = 0x5C,
    K_APPS                = 0x5D,
    K_SLEEP               = 0x5F,
    K_NUMPAD0             = 0x60,
    K_NUMPAD1             = 0x61,
    K_NUMPAD2             = 0x62,
    K_NUMPAD3             = 0x63,
    K_NUMPAD4             = 0x64,
    K_NUMPAD5             = 0x65,
    K_NUMPAD6             = 0x66,
    K_NUMPAD7             = 0x67,
    K_NUMPAD8             = 0x68,
    K_NUMPAD9             = 0x69,
    K_MULTIPLY            = 0x6A,
    K_ADD                 = 0x6B,
    K_SEPARATOR           = 0x6C,
    K_SUBTRACT            = 0x6D,
    K_DECIMAL             = 0x6E,
    K_DIVIDE              = 0x6F,
    K_F1                  = 0x70,
    K_F2                  = 0x71,
    K_F3                  = 0x72,
    K_F4                  = 0x73,
    K_F5                  = 0x74,
    K_F6                  = 0x75,
    K_F7                  = 0x76,
    K_F8                  = 0x77,
    K_F9                  = 0x78,
    K_F10                 = 0x79,
    K_F11                 = 0x7A,
    K_F12                 = 0x7B,
    K_F13                 = 0x7C,
    K_F14                 = 0x7D,
    K_F15                 = 0x7E,
    K_F16                 = 0x7F,
    K_F17                 = 0x80,
    K_F18                 = 0x81,
    K_F19                 = 0x82,
    K_F20                 = 0x83,
    K_F21                 = 0x84,
    K_F22                 = 0x85,
    K_F23                 = 0x86,
    K_F24                 = 0x87,
    K_NUMLOCK             = 0x90,
    K_SCROLL              = 0x91,
    K_OEM_NEC_EQUAL       = 0x92,
    K_OEM_FJ_JISHO        = 0x92,
    K_OEM_FJ_MASSHOU      = 0x93,
    K_OEM_FJ_TOUROKU      = 0x94,
    K_OEM_FJ_LOYA         = 0x95,
    K_OEM_FJ_ROYA         = 0x96,
    K_LSHIFT              = 0xA0,
    K_RSHIFT              = 0xA1,
    K_LCONTROL            = 0xA2,
    K_RCONTROL            = 0xA3,
    K_LMENU               = 0xA4,
    K_RMENU               = 0xA5,
    K_BROWSER_BACK        = 0xA6,
    K_BROWSER_FORWARD     = 0xA7,
    K_BROWSER_REFRESH     = 0xA8,
    K_BROWSER_STOP        = 0xA9,
    K_BROWSER_SEARCH      = 0xAA,
    K_BROWSER_FAVORITES   = 0xAB,
    K_BROWSER_HOME        = 0xAC,
    K_VOLUME_MUTE         = 0xAD,
    K_VOLUME_DOWN         = 0xAE,
    K_VOLUME_UP           = 0xAF,
    K_MEDIA_NEXT_TRACK    = 0xB0,
    K_MEDIA_PREV_TRACK    = 0xB1,
    K_MEDIA_STOP          = 0xB2,
    K_MEDIA_PLAY_PAUSE    = 0xB3,
    K_LAUNCH_MAIL         = 0xB4,
    K_LAUNCH_MEDIA_SELECT = 0xB5,
    K_LAUNCH_APP1         = 0xB6,
    K_LAUNCH_APP2         = 0xB7,
    K_OEM_1               = 0xBA,
    K_OEM_PLUS            = 0xBB,
    K_OEM_COMMA           = 0xBC,
    K_OEM_MINUS           = 0xBD,
    K_OEM_PERIOD          = 0xBE,
    K_OEM_2               = 0xBF,
    K_OEM_3               = 0xC0,
    K_OEM_4               = 0xDB,
    K_OEM_5               = 0xDC,
    K_OEM_6               = 0xDD,
    K_OEM_7               = 0xDE,
    K_OEM_8               = 0xDF,
    K_OEM_AX              = 0xE1,
    K_OEM_102             = 0xE2,
    K_ICO_HELP            = 0xE3,
    K_ICO_00              = 0xE4,
    K_PROCESSKEY          = 0xE5,
    K_ICO_CLEAR           = 0xE6,
    K_PACKET              = 0xE7,
    K_OEM_RESET           = 0xE9,
    K_OEM_JUMP            = 0xEA,
    K_OEM_PA1             = 0xEB,
    K_OEM_PA2             = 0xEC,
    K_OEM_PA3             = 0xED,
    K_OEM_WSCTRL          = 0xEE,
    K_OEM_CUSEL           = 0xEF,
    K_OEM_ATTN            = 0xF0,
    K_OEM_FINISH          = 0xF1,
    K_OEM_COPY            = 0xF2,
    K_OEM_AUTO            = 0xF3,
    K_OEM_ENLW            = 0xF4,
    K_OEM_BACKTAB         = 0xF5,
    K_ATTN                = 0xF6,
    K_CRSEL               = 0xF7,
    K_EXSEL               = 0xF8,
    K_EREOF               = 0xF9,
    K_PLAY                = 0xFA,
    K_ZOOM                = 0xFB,
    K_NONAME              = 0xFC,
    K_PA1                 = 0xFD,
    K_OEM_CLEAR           = 0xFE,
    K_MAX
  };
}