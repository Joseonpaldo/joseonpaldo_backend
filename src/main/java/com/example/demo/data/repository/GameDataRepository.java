package com.example.demo.data.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.data.entity.GameDataEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GameDataRepository {
    final private GameDataRepositoryImpl impl;

    public void createGD(GameDataEntity entity) {
        impl.save(entity);
    }

    public GameDataEntity readGD(Long data_id) {
        return impl.findById(data_id).get();
    }

    public void updateGD(GameDataEntity entity) {
        GameDataEntity data = impl.findById(entity.getData_id()).get();
        // 변환내용 적용
        data.setUser_location(entity.getUser_location());
        data.setUser_estate(entity.getUser_estate());
        data.setMy_turn(entity.getMy_turn());
        impl.save(data);
    }

    public void deleteGD(Long data_id) {
        impl.deleteById(data_id);
    }
}
