package pro.choicemmed.datalib;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import pro.choicemmed.datalib.Cbp1k1Data;
import pro.choicemmed.datalib.CFT308Data;
import pro.choicemmed.datalib.DeviceDisplay;
import pro.choicemmed.datalib.DeviceInfo;
import pro.choicemmed.datalib.EcgData;
import pro.choicemmed.datalib.OxRealTimeData;
import pro.choicemmed.datalib.OxSpotData;
import pro.choicemmed.datalib.UserProfileInfo;
import pro.choicemmed.datalib.W314B4Data;
import pro.choicemmed.datalib.W628Data;

import pro.choicemmed.datalib.Cbp1k1DataDao;
import pro.choicemmed.datalib.CFT308DataDao;
import pro.choicemmed.datalib.DeviceDisplayDao;
import pro.choicemmed.datalib.DeviceInfoDao;
import pro.choicemmed.datalib.EcgDataDao;
import pro.choicemmed.datalib.OxRealTimeDataDao;
import pro.choicemmed.datalib.OxSpotDataDao;
import pro.choicemmed.datalib.UserProfileInfoDao;
import pro.choicemmed.datalib.W314B4DataDao;
import pro.choicemmed.datalib.W628DataDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig cbp1k1DataDaoConfig;
    private final DaoConfig cFT308DataDaoConfig;
    private final DaoConfig deviceDisplayDaoConfig;
    private final DaoConfig deviceInfoDaoConfig;
    private final DaoConfig ecgDataDaoConfig;
    private final DaoConfig oxRealTimeDataDaoConfig;
    private final DaoConfig oxSpotDataDaoConfig;
    private final DaoConfig userProfileInfoDaoConfig;
    private final DaoConfig w314B4DataDaoConfig;
    private final DaoConfig w628DataDaoConfig;

    private final Cbp1k1DataDao cbp1k1DataDao;
    private final CFT308DataDao cFT308DataDao;
    private final DeviceDisplayDao deviceDisplayDao;
    private final DeviceInfoDao deviceInfoDao;
    private final EcgDataDao ecgDataDao;
    private final OxRealTimeDataDao oxRealTimeDataDao;
    private final OxSpotDataDao oxSpotDataDao;
    private final UserProfileInfoDao userProfileInfoDao;
    private final W314B4DataDao w314B4DataDao;
    private final W628DataDao w628DataDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        cbp1k1DataDaoConfig = daoConfigMap.get(Cbp1k1DataDao.class).clone();
        cbp1k1DataDaoConfig.initIdentityScope(type);

        cFT308DataDaoConfig = daoConfigMap.get(CFT308DataDao.class).clone();
        cFT308DataDaoConfig.initIdentityScope(type);

        deviceDisplayDaoConfig = daoConfigMap.get(DeviceDisplayDao.class).clone();
        deviceDisplayDaoConfig.initIdentityScope(type);

        deviceInfoDaoConfig = daoConfigMap.get(DeviceInfoDao.class).clone();
        deviceInfoDaoConfig.initIdentityScope(type);

        ecgDataDaoConfig = daoConfigMap.get(EcgDataDao.class).clone();
        ecgDataDaoConfig.initIdentityScope(type);

        oxRealTimeDataDaoConfig = daoConfigMap.get(OxRealTimeDataDao.class).clone();
        oxRealTimeDataDaoConfig.initIdentityScope(type);

        oxSpotDataDaoConfig = daoConfigMap.get(OxSpotDataDao.class).clone();
        oxSpotDataDaoConfig.initIdentityScope(type);

        userProfileInfoDaoConfig = daoConfigMap.get(UserProfileInfoDao.class).clone();
        userProfileInfoDaoConfig.initIdentityScope(type);

        w314B4DataDaoConfig = daoConfigMap.get(W314B4DataDao.class).clone();
        w314B4DataDaoConfig.initIdentityScope(type);

        w628DataDaoConfig = daoConfigMap.get(W628DataDao.class).clone();
        w628DataDaoConfig.initIdentityScope(type);

        cbp1k1DataDao = new Cbp1k1DataDao(cbp1k1DataDaoConfig, this);
        cFT308DataDao = new CFT308DataDao(cFT308DataDaoConfig, this);
        deviceDisplayDao = new DeviceDisplayDao(deviceDisplayDaoConfig, this);
        deviceInfoDao = new DeviceInfoDao(deviceInfoDaoConfig, this);
        ecgDataDao = new EcgDataDao(ecgDataDaoConfig, this);
        oxRealTimeDataDao = new OxRealTimeDataDao(oxRealTimeDataDaoConfig, this);
        oxSpotDataDao = new OxSpotDataDao(oxSpotDataDaoConfig, this);
        userProfileInfoDao = new UserProfileInfoDao(userProfileInfoDaoConfig, this);
        w314B4DataDao = new W314B4DataDao(w314B4DataDaoConfig, this);
        w628DataDao = new W628DataDao(w628DataDaoConfig, this);

        registerDao(Cbp1k1Data.class, cbp1k1DataDao);
        registerDao(CFT308Data.class, cFT308DataDao);
        registerDao(DeviceDisplay.class, deviceDisplayDao);
        registerDao(DeviceInfo.class, deviceInfoDao);
        registerDao(EcgData.class, ecgDataDao);
        registerDao(OxRealTimeData.class, oxRealTimeDataDao);
        registerDao(OxSpotData.class, oxSpotDataDao);
        registerDao(UserProfileInfo.class, userProfileInfoDao);
        registerDao(W314B4Data.class, w314B4DataDao);
        registerDao(W628Data.class, w628DataDao);
    }
    
    public void clear() {
        cbp1k1DataDaoConfig.clearIdentityScope();
        cFT308DataDaoConfig.clearIdentityScope();
        deviceDisplayDaoConfig.clearIdentityScope();
        deviceInfoDaoConfig.clearIdentityScope();
        ecgDataDaoConfig.clearIdentityScope();
        oxRealTimeDataDaoConfig.clearIdentityScope();
        oxSpotDataDaoConfig.clearIdentityScope();
        userProfileInfoDaoConfig.clearIdentityScope();
        w314B4DataDaoConfig.clearIdentityScope();
        w628DataDaoConfig.clearIdentityScope();
    }

    public Cbp1k1DataDao getCbp1k1DataDao() {
        return cbp1k1DataDao;
    }

    public CFT308DataDao getCFT308DataDao() {
        return cFT308DataDao;
    }

    public DeviceDisplayDao getDeviceDisplayDao() {
        return deviceDisplayDao;
    }

    public DeviceInfoDao getDeviceInfoDao() {
        return deviceInfoDao;
    }

    public EcgDataDao getEcgDataDao() {
        return ecgDataDao;
    }

    public OxRealTimeDataDao getOxRealTimeDataDao() {
        return oxRealTimeDataDao;
    }

    public OxSpotDataDao getOxSpotDataDao() {
        return oxSpotDataDao;
    }

    public UserProfileInfoDao getUserProfileInfoDao() {
        return userProfileInfoDao;
    }

    public W314B4DataDao getW314B4DataDao() {
        return w314B4DataDao;
    }

    public W628DataDao getW628DataDao() {
        return w628DataDao;
    }

}
