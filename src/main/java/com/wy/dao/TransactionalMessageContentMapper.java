package com.wy.dao;

import com.wy.entity.TransactionalMessageContent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TransactionalMessageContentMapper {

    void insert(TransactionalMessageContent record);

    List<TransactionalMessageContent> queryByMessageIds(String messageIds);
}
