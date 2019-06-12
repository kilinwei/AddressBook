package com.wcl.addressbook;

import java.util.List;

/**
 * @项目名： Lockers
 * @包名： com.xyf.lockers.utils
 * @文件名: UserDBManager
 * @创建者: kilin
 * @创建时间: 2019/3/9 21:56
 * @描述： TODO
 */
public class DBManager {

    /**
     * 数据库中插入一条新记录
     */
    public static AddressBean inserStorage2DB(String userName, String comnapy, String phone, String email) {
        AddressBeanDao addressBeanDao = MainAppliction.getInstance().getDaoSession().getAddressBeanDao();
        AddressBean bean = new AddressBean();
        bean.setName(userName);
        bean.setCompany(comnapy);
        bean.setPhone_num(phone);
        bean.setEmail(email);
        addressBeanDao.insert(bean);
        return bean;
    }

    public static AddressBean inserStorage2DB(AddressBean bean) {
        AddressBeanDao addressBeanDao = MainAppliction.getInstance().getDaoSession().getAddressBeanDao();
        addressBeanDao.update(bean);
        return bean;
    }

    public static void delete(AddressBean bean) {
        AddressBeanDao addressBeanDao = MainAppliction.getInstance().getDaoSession().getAddressBeanDao();
        addressBeanDao.delete(bean);
    }


    public static List<AddressBean> getAllRccord() {
        AddressBeanDao addressBeanDao = MainAppliction.getInstance().getDaoSession().getAddressBeanDao();
        List<AddressBean> beans = addressBeanDao.loadAll();
        return beans;
    }

    public static void deleteAll() {
        AddressBeanDao addressBeanDao = MainAppliction.getInstance().getDaoSession().getAddressBeanDao();
        addressBeanDao.deleteAll();
    }

    public static List<AddressBean> queryByName(String name) {
        AddressBeanDao addressBeanDao = MainAppliction.getInstance().getDaoSession().getAddressBeanDao();
        List<AddressBean> nameLikes = addressBeanDao.queryBuilder().where(AddressBeanDao.Properties.Name.like("%" + name + "%")).list();
        return nameLikes;
    }

    public static List<AddressBean> queryByPhoneNum(String phone) {
        AddressBeanDao addressBeanDao = MainAppliction.getInstance().getDaoSession().getAddressBeanDao();
        List<AddressBean> phoneLikes = addressBeanDao.queryBuilder().where(AddressBeanDao.Properties.Phone_num.like("%" + phone + "%")).list();
        return phoneLikes;
    }
}
