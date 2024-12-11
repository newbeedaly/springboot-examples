package cn.newbeedaly.multi.datasource.domain.ds.service;


import cn.newbeedaly.multi.datasource.adaptor.ds.dao.po.User;

public interface IUserService {

    User getOneDSUser(Integer id);
    User getTwoDSUser(Integer id);
}
