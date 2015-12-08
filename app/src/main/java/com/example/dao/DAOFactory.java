package com.example.dao;

/**
 * Created by kathy on 2015/12/3.
 */

import android.content.Context;
public class DAOFactory {
    // 单例
    private static DAOFactory instance;
    // private AlarmBasicDAO alarmBasicDAO;
    private MessageDao messageDAO;

    private DAOFactory() {
    }

    // 获取单例
    public static DAOFactory getInstance() {
        if (instance == null) {
            synchronized (DAOFactory.class) {
                // check twice
                if (instance == null) {
                    instance = new DAOFactory();
                }
            }
        }
        return instance;
    }

	/*
	 * public AlarmBasicDAO getAlarmBasicDAO(Context context) { if
	 * (alarmBasicDAO == null) alarmBasicDAO = new AlarmBasicDAO(context);
	 * return alarmBasicDAO; }
	 */

    public MessageDao getMessageDAO(Context context) {
        if (messageDAO == null)
            messageDAO = new MessageDao(context);
        return messageDAO;
    }
}
