package com.razacx.domain.service;

public interface IServiceHolder {

    IMessageService getMessageService();
    IPersonService getPersonService();
    ITopicService getTopicService();

}
