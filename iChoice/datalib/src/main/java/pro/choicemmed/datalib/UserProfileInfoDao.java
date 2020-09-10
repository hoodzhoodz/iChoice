package pro.choicemmed.datalib;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_PROFILE_INFO".
*/
public class UserProfileInfoDao extends AbstractDao<UserProfileInfo, String> {

    public static final String TABLENAME = "USER_PROFILE_INFO";

    /**
     * Properties of entity UserProfileInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "Id", true, "ID");
        public final static Property UserId = new Property(1, String.class, "UserId", false, "USER_ID");
        public final static Property Email = new Property(2, String.class, "Email", false, "EMAIL");
        public final static Property Password = new Property(3, String.class, "Password", false, "PASSWORD");
        public final static Property Gender = new Property(4, String.class, "Gender", false, "GENDER");
        public final static Property Birthday = new Property(5, String.class, "Birthday", false, "BIRTHDAY");
        public final static Property LengthShowUnitSystem = new Property(6, String.class, "LengthShowUnitSystem", false, "LENGTH_SHOW_UNIT_SYSTEM");
        public final static Property Height = new Property(7, String.class, "Height", false, "HEIGHT");
        public final static Property WeightShowUnitSystem = new Property(8, String.class, "WeightShowUnitSystem", false, "WEIGHT_SHOW_UNIT_SYSTEM");
        public final static Property Weight = new Property(9, String.class, "Weight", false, "WEIGHT");
        public final static Property StartWeight = new Property(10, String.class, "StartWeight", false, "START_WEIGHT");
        public final static Property TimeZoneId = new Property(11, String.class, "TimeZoneId", false, "TIME_ZONE_ID");
        public final static Property TimeZoneName = new Property(12, String.class, "TimeZoneName", false, "TIME_ZONE_NAME");
        public final static Property TimeDifference = new Property(13, String.class, "TimeDifference", false, "TIME_DIFFERENCE");
        public final static Property CountryId = new Property(14, String.class, "CountryId", false, "COUNTRY_ID");
        public final static Property CountryName = new Property(15, String.class, "CountryName", false, "COUNTRY_NAME");
        public final static Property ShortName = new Property(16, String.class, "ShortName", false, "SHORT_NAME");
        public final static Property City = new Property(17, String.class, "City", false, "CITY");
        public final static Property PostalCode = new Property(18, String.class, "PostalCode", false, "POSTAL_CODE");
        public final static Property AboutMe = new Property(19, String.class, "AboutMe", false, "ABOUT_ME");
        public final static Property TimeSystem = new Property(20, String.class, "TimeSystem", false, "TIME_SYSTEM");
        public final static Property TemperatureUnit = new Property(21, String.class, "TemperatureUnit", false, "TEMPERATURE_UNIT");
        public final static Property StrideLength = new Property(22, String.class, "StrideLength", false, "STRIDE_LENGTH");
        public final static Property RunningStrideLength = new Property(23, String.class, "RunningStrideLength", false, "RUNNING_STRIDE_LENGTH");
        public final static Property FullName = new Property(24, String.class, "FullName", false, "FULL_NAME");
        public final static Property NickName = new Property(25, String.class, "NickName", false, "NICK_NAME");
        public final static Property PhotoExtension = new Property(26, String.class, "PhotoExtension", false, "PHOTO_EXTENSION");
        public final static Property Photo100x100 = new Property(27, String.class, "Photo100x100", false, "PHOTO100X100");
        public final static Property Photo200x200 = new Property(28, String.class, "Photo200x200", false, "PHOTO200X200");
        public final static Property Photo500x400 = new Property(29, String.class, "Photo500x400", false, "PHOTO500X400");
        public final static Property ProfileItemVisitLevelSeries = new Property(30, String.class, "ProfileItemVisitLevelSeries", false, "PROFILE_ITEM_VISIT_LEVEL_SERIES");
        public final static Property UserGroupId = new Property(31, String.class, "UserGroupId", false, "USER_GROUP_ID");
        public final static Property UserStatus = new Property(32, String.class, "UserStatus", false, "USER_STATUS");
        public final static Property SignupDateTime = new Property(33, String.class, "SignupDateTime", false, "SIGNUP_DATE_TIME");
        public final static Property SignupIP = new Property(34, String.class, "SignupIP", false, "SIGNUP_IP");
        public final static Property LoginTimes = new Property(35, String.class, "LoginTimes", false, "LOGIN_TIMES");
        public final static Property LastLoginDateTime = new Property(36, String.class, "LastLoginDateTime", false, "LAST_LOGIN_DATE_TIME");
        public final static Property LastLoginIP = new Property(37, String.class, "LastLoginIP", false, "LAST_LOGIN_IP");
        public final static Property BPUnitSystem = new Property(38, String.class, "BPUnitSystem", false, "BPUNIT_SYSTEM");
        public final static Property IsValidEmail = new Property(39, boolean.class, "IsValidEmail", false, "IS_VALID_EMAIL");
        public final static Property Source = new Property(40, String.class, "Source", false, "SOURCE");
        public final static Property RecordCount = new Property(41, String.class, "RecordCount", false, "RECORD_COUNT");
        public final static Property UtcNow = new Property(42, String.class, "UtcNow", false, "UTC_NOW");
        public final static Property UnreadMessageNum = new Property(43, String.class, "UnreadMessageNum", false, "UNREAD_MESSAGE_NUM");
        public final static Property SysMessageModellist = new Property(44, String.class, "SysMessageModellist", false, "SYS_MESSAGE_MODELLIST");
        public final static Property UpdateWeight = new Property(45, String.class, "UpdateWeight", false, "UPDATE_WEIGHT");
        public final static Property DueDate = new Property(46, String.class, "DueDate", false, "DUE_DATE");
        public final static Property GenstationDate = new Property(47, String.class, "GenstationDate", false, "GENSTATION_DATE");
        public final static Property FirstName = new Property(48, String.class, "FirstName", false, "FIRST_NAME");
        public final static Property FamilyName = new Property(49, String.class, "FamilyName", false, "FAMILY_NAME");
        public final static Property RegType = new Property(50, String.class, "RegType", false, "REG_TYPE");
        public final static Property Phone = new Property(51, String.class, "Phone", false, "PHONE");
        public final static Property LinkId = new Property(52, String.class, "LinkId", false, "LINK_ID");
        public final static Property DelState = new Property(53, boolean.class, "DelState", false, "DEL_STATE");
        public final static Property LastUpdateTime = new Property(54, String.class, "LastUpdateTime", false, "LAST_UPDATE_TIME");
        public final static Property IsUnit = new Property(55, boolean.class, "isUnit", false, "IS_UNIT");
    }


    public UserProfileInfoDao(DaoConfig config) {
        super(config);
    }
    
    public UserProfileInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_PROFILE_INFO\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: Id
                "\"USER_ID\" TEXT," + // 1: UserId
                "\"EMAIL\" TEXT," + // 2: Email
                "\"PASSWORD\" TEXT," + // 3: Password
                "\"GENDER\" TEXT," + // 4: Gender
                "\"BIRTHDAY\" TEXT," + // 5: Birthday
                "\"LENGTH_SHOW_UNIT_SYSTEM\" TEXT," + // 6: LengthShowUnitSystem
                "\"HEIGHT\" TEXT," + // 7: Height
                "\"WEIGHT_SHOW_UNIT_SYSTEM\" TEXT," + // 8: WeightShowUnitSystem
                "\"WEIGHT\" TEXT," + // 9: Weight
                "\"START_WEIGHT\" TEXT," + // 10: StartWeight
                "\"TIME_ZONE_ID\" TEXT," + // 11: TimeZoneId
                "\"TIME_ZONE_NAME\" TEXT," + // 12: TimeZoneName
                "\"TIME_DIFFERENCE\" TEXT," + // 13: TimeDifference
                "\"COUNTRY_ID\" TEXT," + // 14: CountryId
                "\"COUNTRY_NAME\" TEXT," + // 15: CountryName
                "\"SHORT_NAME\" TEXT," + // 16: ShortName
                "\"CITY\" TEXT," + // 17: City
                "\"POSTAL_CODE\" TEXT," + // 18: PostalCode
                "\"ABOUT_ME\" TEXT," + // 19: AboutMe
                "\"TIME_SYSTEM\" TEXT," + // 20: TimeSystem
                "\"TEMPERATURE_UNIT\" TEXT," + // 21: TemperatureUnit
                "\"STRIDE_LENGTH\" TEXT," + // 22: StrideLength
                "\"RUNNING_STRIDE_LENGTH\" TEXT," + // 23: RunningStrideLength
                "\"FULL_NAME\" TEXT," + // 24: FullName
                "\"NICK_NAME\" TEXT," + // 25: NickName
                "\"PHOTO_EXTENSION\" TEXT," + // 26: PhotoExtension
                "\"PHOTO100X100\" TEXT," + // 27: Photo100x100
                "\"PHOTO200X200\" TEXT," + // 28: Photo200x200
                "\"PHOTO500X400\" TEXT," + // 29: Photo500x400
                "\"PROFILE_ITEM_VISIT_LEVEL_SERIES\" TEXT," + // 30: ProfileItemVisitLevelSeries
                "\"USER_GROUP_ID\" TEXT," + // 31: UserGroupId
                "\"USER_STATUS\" TEXT," + // 32: UserStatus
                "\"SIGNUP_DATE_TIME\" TEXT," + // 33: SignupDateTime
                "\"SIGNUP_IP\" TEXT," + // 34: SignupIP
                "\"LOGIN_TIMES\" TEXT," + // 35: LoginTimes
                "\"LAST_LOGIN_DATE_TIME\" TEXT," + // 36: LastLoginDateTime
                "\"LAST_LOGIN_IP\" TEXT," + // 37: LastLoginIP
                "\"BPUNIT_SYSTEM\" TEXT," + // 38: BPUnitSystem
                "\"IS_VALID_EMAIL\" INTEGER NOT NULL ," + // 39: IsValidEmail
                "\"SOURCE\" TEXT," + // 40: Source
                "\"RECORD_COUNT\" TEXT," + // 41: RecordCount
                "\"UTC_NOW\" TEXT," + // 42: UtcNow
                "\"UNREAD_MESSAGE_NUM\" TEXT," + // 43: UnreadMessageNum
                "\"SYS_MESSAGE_MODELLIST\" TEXT," + // 44: SysMessageModellist
                "\"UPDATE_WEIGHT\" TEXT," + // 45: UpdateWeight
                "\"DUE_DATE\" TEXT," + // 46: DueDate
                "\"GENSTATION_DATE\" TEXT," + // 47: GenstationDate
                "\"FIRST_NAME\" TEXT," + // 48: FirstName
                "\"FAMILY_NAME\" TEXT," + // 49: FamilyName
                "\"REG_TYPE\" TEXT," + // 50: RegType
                "\"PHONE\" TEXT," + // 51: Phone
                "\"LINK_ID\" TEXT," + // 52: LinkId
                "\"DEL_STATE\" INTEGER NOT NULL ," + // 53: DelState
                "\"LAST_UPDATE_TIME\" TEXT," + // 54: LastUpdateTime
                "\"IS_UNIT\" INTEGER NOT NULL );"); // 55: isUnit
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_PROFILE_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserProfileInfo entity) {
        stmt.clearBindings();
 
        String Id = entity.getId();
        if (Id != null) {
            stmt.bindString(1, Id);
        }
 
        String UserId = entity.getUserId();
        if (UserId != null) {
            stmt.bindString(2, UserId);
        }
 
        String Email = entity.getEmail();
        if (Email != null) {
            stmt.bindString(3, Email);
        }
 
        String Password = entity.getPassword();
        if (Password != null) {
            stmt.bindString(4, Password);
        }
 
        String Gender = entity.getGender();
        if (Gender != null) {
            stmt.bindString(5, Gender);
        }
 
        String Birthday = entity.getBirthday();
        if (Birthday != null) {
            stmt.bindString(6, Birthday);
        }
 
        String LengthShowUnitSystem = entity.getLengthShowUnitSystem();
        if (LengthShowUnitSystem != null) {
            stmt.bindString(7, LengthShowUnitSystem);
        }
 
        String Height = entity.getHeight();
        if (Height != null) {
            stmt.bindString(8, Height);
        }
 
        String WeightShowUnitSystem = entity.getWeightShowUnitSystem();
        if (WeightShowUnitSystem != null) {
            stmt.bindString(9, WeightShowUnitSystem);
        }
 
        String Weight = entity.getWeight();
        if (Weight != null) {
            stmt.bindString(10, Weight);
        }
 
        String StartWeight = entity.getStartWeight();
        if (StartWeight != null) {
            stmt.bindString(11, StartWeight);
        }
 
        String TimeZoneId = entity.getTimeZoneId();
        if (TimeZoneId != null) {
            stmt.bindString(12, TimeZoneId);
        }
 
        String TimeZoneName = entity.getTimeZoneName();
        if (TimeZoneName != null) {
            stmt.bindString(13, TimeZoneName);
        }
 
        String TimeDifference = entity.getTimeDifference();
        if (TimeDifference != null) {
            stmt.bindString(14, TimeDifference);
        }
 
        String CountryId = entity.getCountryId();
        if (CountryId != null) {
            stmt.bindString(15, CountryId);
        }
 
        String CountryName = entity.getCountryName();
        if (CountryName != null) {
            stmt.bindString(16, CountryName);
        }
 
        String ShortName = entity.getShortName();
        if (ShortName != null) {
            stmt.bindString(17, ShortName);
        }
 
        String City = entity.getCity();
        if (City != null) {
            stmt.bindString(18, City);
        }
 
        String PostalCode = entity.getPostalCode();
        if (PostalCode != null) {
            stmt.bindString(19, PostalCode);
        }
 
        String AboutMe = entity.getAboutMe();
        if (AboutMe != null) {
            stmt.bindString(20, AboutMe);
        }
 
        String TimeSystem = entity.getTimeSystem();
        if (TimeSystem != null) {
            stmt.bindString(21, TimeSystem);
        }
 
        String TemperatureUnit = entity.getTemperatureUnit();
        if (TemperatureUnit != null) {
            stmt.bindString(22, TemperatureUnit);
        }
 
        String StrideLength = entity.getStrideLength();
        if (StrideLength != null) {
            stmt.bindString(23, StrideLength);
        }
 
        String RunningStrideLength = entity.getRunningStrideLength();
        if (RunningStrideLength != null) {
            stmt.bindString(24, RunningStrideLength);
        }
 
        String FullName = entity.getFullName();
        if (FullName != null) {
            stmt.bindString(25, FullName);
        }
 
        String NickName = entity.getNickName();
        if (NickName != null) {
            stmt.bindString(26, NickName);
        }
 
        String PhotoExtension = entity.getPhotoExtension();
        if (PhotoExtension != null) {
            stmt.bindString(27, PhotoExtension);
        }
 
        String Photo100x100 = entity.getPhoto100x100();
        if (Photo100x100 != null) {
            stmt.bindString(28, Photo100x100);
        }
 
        String Photo200x200 = entity.getPhoto200x200();
        if (Photo200x200 != null) {
            stmt.bindString(29, Photo200x200);
        }
 
        String Photo500x400 = entity.getPhoto500x400();
        if (Photo500x400 != null) {
            stmt.bindString(30, Photo500x400);
        }
 
        String ProfileItemVisitLevelSeries = entity.getProfileItemVisitLevelSeries();
        if (ProfileItemVisitLevelSeries != null) {
            stmt.bindString(31, ProfileItemVisitLevelSeries);
        }
 
        String UserGroupId = entity.getUserGroupId();
        if (UserGroupId != null) {
            stmt.bindString(32, UserGroupId);
        }
 
        String UserStatus = entity.getUserStatus();
        if (UserStatus != null) {
            stmt.bindString(33, UserStatus);
        }
 
        String SignupDateTime = entity.getSignupDateTime();
        if (SignupDateTime != null) {
            stmt.bindString(34, SignupDateTime);
        }
 
        String SignupIP = entity.getSignupIP();
        if (SignupIP != null) {
            stmt.bindString(35, SignupIP);
        }
 
        String LoginTimes = entity.getLoginTimes();
        if (LoginTimes != null) {
            stmt.bindString(36, LoginTimes);
        }
 
        String LastLoginDateTime = entity.getLastLoginDateTime();
        if (LastLoginDateTime != null) {
            stmt.bindString(37, LastLoginDateTime);
        }
 
        String LastLoginIP = entity.getLastLoginIP();
        if (LastLoginIP != null) {
            stmt.bindString(38, LastLoginIP);
        }
 
        String BPUnitSystem = entity.getBPUnitSystem();
        if (BPUnitSystem != null) {
            stmt.bindString(39, BPUnitSystem);
        }
        stmt.bindLong(40, entity.getIsValidEmail() ? 1L: 0L);
 
        String Source = entity.getSource();
        if (Source != null) {
            stmt.bindString(41, Source);
        }
 
        String RecordCount = entity.getRecordCount();
        if (RecordCount != null) {
            stmt.bindString(42, RecordCount);
        }
 
        String UtcNow = entity.getUtcNow();
        if (UtcNow != null) {
            stmt.bindString(43, UtcNow);
        }
 
        String UnreadMessageNum = entity.getUnreadMessageNum();
        if (UnreadMessageNum != null) {
            stmt.bindString(44, UnreadMessageNum);
        }
 
        String SysMessageModellist = entity.getSysMessageModellist();
        if (SysMessageModellist != null) {
            stmt.bindString(45, SysMessageModellist);
        }
 
        String UpdateWeight = entity.getUpdateWeight();
        if (UpdateWeight != null) {
            stmt.bindString(46, UpdateWeight);
        }
 
        String DueDate = entity.getDueDate();
        if (DueDate != null) {
            stmt.bindString(47, DueDate);
        }
 
        String GenstationDate = entity.getGenstationDate();
        if (GenstationDate != null) {
            stmt.bindString(48, GenstationDate);
        }
 
        String FirstName = entity.getFirstName();
        if (FirstName != null) {
            stmt.bindString(49, FirstName);
        }
 
        String FamilyName = entity.getFamilyName();
        if (FamilyName != null) {
            stmt.bindString(50, FamilyName);
        }
 
        String RegType = entity.getRegType();
        if (RegType != null) {
            stmt.bindString(51, RegType);
        }
 
        String Phone = entity.getPhone();
        if (Phone != null) {
            stmt.bindString(52, Phone);
        }
 
        String LinkId = entity.getLinkId();
        if (LinkId != null) {
            stmt.bindString(53, LinkId);
        }
        stmt.bindLong(54, entity.getDelState() ? 1L: 0L);
 
        String LastUpdateTime = entity.getLastUpdateTime();
        if (LastUpdateTime != null) {
            stmt.bindString(55, LastUpdateTime);
        }
        stmt.bindLong(56, entity.getIsUnit() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserProfileInfo entity) {
        stmt.clearBindings();
 
        String Id = entity.getId();
        if (Id != null) {
            stmt.bindString(1, Id);
        }
 
        String UserId = entity.getUserId();
        if (UserId != null) {
            stmt.bindString(2, UserId);
        }
 
        String Email = entity.getEmail();
        if (Email != null) {
            stmt.bindString(3, Email);
        }
 
        String Password = entity.getPassword();
        if (Password != null) {
            stmt.bindString(4, Password);
        }
 
        String Gender = entity.getGender();
        if (Gender != null) {
            stmt.bindString(5, Gender);
        }
 
        String Birthday = entity.getBirthday();
        if (Birthday != null) {
            stmt.bindString(6, Birthday);
        }
 
        String LengthShowUnitSystem = entity.getLengthShowUnitSystem();
        if (LengthShowUnitSystem != null) {
            stmt.bindString(7, LengthShowUnitSystem);
        }
 
        String Height = entity.getHeight();
        if (Height != null) {
            stmt.bindString(8, Height);
        }
 
        String WeightShowUnitSystem = entity.getWeightShowUnitSystem();
        if (WeightShowUnitSystem != null) {
            stmt.bindString(9, WeightShowUnitSystem);
        }
 
        String Weight = entity.getWeight();
        if (Weight != null) {
            stmt.bindString(10, Weight);
        }
 
        String StartWeight = entity.getStartWeight();
        if (StartWeight != null) {
            stmt.bindString(11, StartWeight);
        }
 
        String TimeZoneId = entity.getTimeZoneId();
        if (TimeZoneId != null) {
            stmt.bindString(12, TimeZoneId);
        }
 
        String TimeZoneName = entity.getTimeZoneName();
        if (TimeZoneName != null) {
            stmt.bindString(13, TimeZoneName);
        }
 
        String TimeDifference = entity.getTimeDifference();
        if (TimeDifference != null) {
            stmt.bindString(14, TimeDifference);
        }
 
        String CountryId = entity.getCountryId();
        if (CountryId != null) {
            stmt.bindString(15, CountryId);
        }
 
        String CountryName = entity.getCountryName();
        if (CountryName != null) {
            stmt.bindString(16, CountryName);
        }
 
        String ShortName = entity.getShortName();
        if (ShortName != null) {
            stmt.bindString(17, ShortName);
        }
 
        String City = entity.getCity();
        if (City != null) {
            stmt.bindString(18, City);
        }
 
        String PostalCode = entity.getPostalCode();
        if (PostalCode != null) {
            stmt.bindString(19, PostalCode);
        }
 
        String AboutMe = entity.getAboutMe();
        if (AboutMe != null) {
            stmt.bindString(20, AboutMe);
        }
 
        String TimeSystem = entity.getTimeSystem();
        if (TimeSystem != null) {
            stmt.bindString(21, TimeSystem);
        }
 
        String TemperatureUnit = entity.getTemperatureUnit();
        if (TemperatureUnit != null) {
            stmt.bindString(22, TemperatureUnit);
        }
 
        String StrideLength = entity.getStrideLength();
        if (StrideLength != null) {
            stmt.bindString(23, StrideLength);
        }
 
        String RunningStrideLength = entity.getRunningStrideLength();
        if (RunningStrideLength != null) {
            stmt.bindString(24, RunningStrideLength);
        }
 
        String FullName = entity.getFullName();
        if (FullName != null) {
            stmt.bindString(25, FullName);
        }
 
        String NickName = entity.getNickName();
        if (NickName != null) {
            stmt.bindString(26, NickName);
        }
 
        String PhotoExtension = entity.getPhotoExtension();
        if (PhotoExtension != null) {
            stmt.bindString(27, PhotoExtension);
        }
 
        String Photo100x100 = entity.getPhoto100x100();
        if (Photo100x100 != null) {
            stmt.bindString(28, Photo100x100);
        }
 
        String Photo200x200 = entity.getPhoto200x200();
        if (Photo200x200 != null) {
            stmt.bindString(29, Photo200x200);
        }
 
        String Photo500x400 = entity.getPhoto500x400();
        if (Photo500x400 != null) {
            stmt.bindString(30, Photo500x400);
        }
 
        String ProfileItemVisitLevelSeries = entity.getProfileItemVisitLevelSeries();
        if (ProfileItemVisitLevelSeries != null) {
            stmt.bindString(31, ProfileItemVisitLevelSeries);
        }
 
        String UserGroupId = entity.getUserGroupId();
        if (UserGroupId != null) {
            stmt.bindString(32, UserGroupId);
        }
 
        String UserStatus = entity.getUserStatus();
        if (UserStatus != null) {
            stmt.bindString(33, UserStatus);
        }
 
        String SignupDateTime = entity.getSignupDateTime();
        if (SignupDateTime != null) {
            stmt.bindString(34, SignupDateTime);
        }
 
        String SignupIP = entity.getSignupIP();
        if (SignupIP != null) {
            stmt.bindString(35, SignupIP);
        }
 
        String LoginTimes = entity.getLoginTimes();
        if (LoginTimes != null) {
            stmt.bindString(36, LoginTimes);
        }
 
        String LastLoginDateTime = entity.getLastLoginDateTime();
        if (LastLoginDateTime != null) {
            stmt.bindString(37, LastLoginDateTime);
        }
 
        String LastLoginIP = entity.getLastLoginIP();
        if (LastLoginIP != null) {
            stmt.bindString(38, LastLoginIP);
        }
 
        String BPUnitSystem = entity.getBPUnitSystem();
        if (BPUnitSystem != null) {
            stmt.bindString(39, BPUnitSystem);
        }
        stmt.bindLong(40, entity.getIsValidEmail() ? 1L: 0L);
 
        String Source = entity.getSource();
        if (Source != null) {
            stmt.bindString(41, Source);
        }
 
        String RecordCount = entity.getRecordCount();
        if (RecordCount != null) {
            stmt.bindString(42, RecordCount);
        }
 
        String UtcNow = entity.getUtcNow();
        if (UtcNow != null) {
            stmt.bindString(43, UtcNow);
        }
 
        String UnreadMessageNum = entity.getUnreadMessageNum();
        if (UnreadMessageNum != null) {
            stmt.bindString(44, UnreadMessageNum);
        }
 
        String SysMessageModellist = entity.getSysMessageModellist();
        if (SysMessageModellist != null) {
            stmt.bindString(45, SysMessageModellist);
        }
 
        String UpdateWeight = entity.getUpdateWeight();
        if (UpdateWeight != null) {
            stmt.bindString(46, UpdateWeight);
        }
 
        String DueDate = entity.getDueDate();
        if (DueDate != null) {
            stmt.bindString(47, DueDate);
        }
 
        String GenstationDate = entity.getGenstationDate();
        if (GenstationDate != null) {
            stmt.bindString(48, GenstationDate);
        }
 
        String FirstName = entity.getFirstName();
        if (FirstName != null) {
            stmt.bindString(49, FirstName);
        }
 
        String FamilyName = entity.getFamilyName();
        if (FamilyName != null) {
            stmt.bindString(50, FamilyName);
        }
 
        String RegType = entity.getRegType();
        if (RegType != null) {
            stmt.bindString(51, RegType);
        }
 
        String Phone = entity.getPhone();
        if (Phone != null) {
            stmt.bindString(52, Phone);
        }
 
        String LinkId = entity.getLinkId();
        if (LinkId != null) {
            stmt.bindString(53, LinkId);
        }
        stmt.bindLong(54, entity.getDelState() ? 1L: 0L);
 
        String LastUpdateTime = entity.getLastUpdateTime();
        if (LastUpdateTime != null) {
            stmt.bindString(55, LastUpdateTime);
        }
        stmt.bindLong(56, entity.getIsUnit() ? 1L: 0L);
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public UserProfileInfo readEntity(Cursor cursor, int offset) {
        UserProfileInfo entity = new UserProfileInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // Id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // UserId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // Email
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // Password
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // Gender
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // Birthday
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // LengthShowUnitSystem
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // Height
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // WeightShowUnitSystem
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // Weight
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // StartWeight
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // TimeZoneId
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // TimeZoneName
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // TimeDifference
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // CountryId
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // CountryName
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // ShortName
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // City
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // PostalCode
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // AboutMe
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // TimeSystem
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // TemperatureUnit
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // StrideLength
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // RunningStrideLength
            cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24), // FullName
            cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25), // NickName
            cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26), // PhotoExtension
            cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27), // Photo100x100
            cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28), // Photo200x200
            cursor.isNull(offset + 29) ? null : cursor.getString(offset + 29), // Photo500x400
            cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30), // ProfileItemVisitLevelSeries
            cursor.isNull(offset + 31) ? null : cursor.getString(offset + 31), // UserGroupId
            cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32), // UserStatus
            cursor.isNull(offset + 33) ? null : cursor.getString(offset + 33), // SignupDateTime
            cursor.isNull(offset + 34) ? null : cursor.getString(offset + 34), // SignupIP
            cursor.isNull(offset + 35) ? null : cursor.getString(offset + 35), // LoginTimes
            cursor.isNull(offset + 36) ? null : cursor.getString(offset + 36), // LastLoginDateTime
            cursor.isNull(offset + 37) ? null : cursor.getString(offset + 37), // LastLoginIP
            cursor.isNull(offset + 38) ? null : cursor.getString(offset + 38), // BPUnitSystem
            cursor.getShort(offset + 39) != 0, // IsValidEmail
            cursor.isNull(offset + 40) ? null : cursor.getString(offset + 40), // Source
            cursor.isNull(offset + 41) ? null : cursor.getString(offset + 41), // RecordCount
            cursor.isNull(offset + 42) ? null : cursor.getString(offset + 42), // UtcNow
            cursor.isNull(offset + 43) ? null : cursor.getString(offset + 43), // UnreadMessageNum
            cursor.isNull(offset + 44) ? null : cursor.getString(offset + 44), // SysMessageModellist
            cursor.isNull(offset + 45) ? null : cursor.getString(offset + 45), // UpdateWeight
            cursor.isNull(offset + 46) ? null : cursor.getString(offset + 46), // DueDate
            cursor.isNull(offset + 47) ? null : cursor.getString(offset + 47), // GenstationDate
            cursor.isNull(offset + 48) ? null : cursor.getString(offset + 48), // FirstName
            cursor.isNull(offset + 49) ? null : cursor.getString(offset + 49), // FamilyName
            cursor.isNull(offset + 50) ? null : cursor.getString(offset + 50), // RegType
            cursor.isNull(offset + 51) ? null : cursor.getString(offset + 51), // Phone
            cursor.isNull(offset + 52) ? null : cursor.getString(offset + 52), // LinkId
            cursor.getShort(offset + 53) != 0, // DelState
            cursor.isNull(offset + 54) ? null : cursor.getString(offset + 54), // LastUpdateTime
            cursor.getShort(offset + 55) != 0 // isUnit
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserProfileInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setUserId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setEmail(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPassword(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setGender(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setBirthday(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setLengthShowUnitSystem(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setHeight(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setWeightShowUnitSystem(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setWeight(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setStartWeight(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setTimeZoneId(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setTimeZoneName(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setTimeDifference(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setCountryId(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setCountryName(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setShortName(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setCity(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setPostalCode(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setAboutMe(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setTimeSystem(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setTemperatureUnit(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setStrideLength(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setRunningStrideLength(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setFullName(cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24));
        entity.setNickName(cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25));
        entity.setPhotoExtension(cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26));
        entity.setPhoto100x100(cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27));
        entity.setPhoto200x200(cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28));
        entity.setPhoto500x400(cursor.isNull(offset + 29) ? null : cursor.getString(offset + 29));
        entity.setProfileItemVisitLevelSeries(cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30));
        entity.setUserGroupId(cursor.isNull(offset + 31) ? null : cursor.getString(offset + 31));
        entity.setUserStatus(cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32));
        entity.setSignupDateTime(cursor.isNull(offset + 33) ? null : cursor.getString(offset + 33));
        entity.setSignupIP(cursor.isNull(offset + 34) ? null : cursor.getString(offset + 34));
        entity.setLoginTimes(cursor.isNull(offset + 35) ? null : cursor.getString(offset + 35));
        entity.setLastLoginDateTime(cursor.isNull(offset + 36) ? null : cursor.getString(offset + 36));
        entity.setLastLoginIP(cursor.isNull(offset + 37) ? null : cursor.getString(offset + 37));
        entity.setBPUnitSystem(cursor.isNull(offset + 38) ? null : cursor.getString(offset + 38));
        entity.setIsValidEmail(cursor.getShort(offset + 39) != 0);
        entity.setSource(cursor.isNull(offset + 40) ? null : cursor.getString(offset + 40));
        entity.setRecordCount(cursor.isNull(offset + 41) ? null : cursor.getString(offset + 41));
        entity.setUtcNow(cursor.isNull(offset + 42) ? null : cursor.getString(offset + 42));
        entity.setUnreadMessageNum(cursor.isNull(offset + 43) ? null : cursor.getString(offset + 43));
        entity.setSysMessageModellist(cursor.isNull(offset + 44) ? null : cursor.getString(offset + 44));
        entity.setUpdateWeight(cursor.isNull(offset + 45) ? null : cursor.getString(offset + 45));
        entity.setDueDate(cursor.isNull(offset + 46) ? null : cursor.getString(offset + 46));
        entity.setGenstationDate(cursor.isNull(offset + 47) ? null : cursor.getString(offset + 47));
        entity.setFirstName(cursor.isNull(offset + 48) ? null : cursor.getString(offset + 48));
        entity.setFamilyName(cursor.isNull(offset + 49) ? null : cursor.getString(offset + 49));
        entity.setRegType(cursor.isNull(offset + 50) ? null : cursor.getString(offset + 50));
        entity.setPhone(cursor.isNull(offset + 51) ? null : cursor.getString(offset + 51));
        entity.setLinkId(cursor.isNull(offset + 52) ? null : cursor.getString(offset + 52));
        entity.setDelState(cursor.getShort(offset + 53) != 0);
        entity.setLastUpdateTime(cursor.isNull(offset + 54) ? null : cursor.getString(offset + 54));
        entity.setIsUnit(cursor.getShort(offset + 55) != 0);
     }
    
    @Override
    protected final String updateKeyAfterInsert(UserProfileInfo entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(UserProfileInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserProfileInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
