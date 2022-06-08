package main.persistence;

import main.domain.DrivingTip;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class DrivingTipRepositoryInMemory implements DrivingTipRepository{
    List<DrivingTip> drivingTips = new ArrayList<>();

    public DrivingTipRepositoryInMemory() {
    }

    @Override
    public List<DrivingTip> findAll() {
        return drivingTips;
    }

    @Override
    public <S extends DrivingTip> S save(S entity) {
        drivingTips.add(entity);
        return entity;
    }

    @Override
    public int deleteDrivingTipById(Long id) {
        Optional<DrivingTip> drivingTip = drivingTips.stream().filter(tip -> Objects.equals(tip.getId(), id)).findFirst();
        if(drivingTip.isPresent()){
            drivingTips.remove(drivingTip.get());
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public void delete(DrivingTip entity) {
        drivingTips.remove(entity);
    }

    @Override
    public long count() {
        return drivingTips.size();
    }

    @Override
    public List<DrivingTip> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<DrivingTip> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<DrivingTip> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends DrivingTip> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends DrivingTip> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<DrivingTip> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends DrivingTip> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends DrivingTip> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<DrivingTip> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public DrivingTip getOne(Long aLong) {
        return null;
    }

    @Override
    public DrivingTip getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends DrivingTip> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends DrivingTip> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends DrivingTip> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends DrivingTip> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends DrivingTip> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends DrivingTip> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends DrivingTip, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<DrivingTip> getAllByUsername(String username) {
        return null;
    }

    @Override
    public Long getLastId() {
        return null;
    }
}
