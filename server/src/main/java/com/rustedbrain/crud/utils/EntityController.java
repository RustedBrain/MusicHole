package com.rustedbrain.crud.utils;

import com.rustedbrain.networks.model.members.Account;
import com.rustedbrain.networks.model.music.*;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by RustedBrain on 25.02.2016.
 */
public class EntityController {
    private final Session session;
    private ICRUD<Account> accountUtil;
    private ICRUD<Album> albumUtil;
    private ICRUD<Genre> genreUtil;
    private ICRUD<Group> groupUtil;
    private ICRUD<Song> songUtil;
    private ICRUD<Member> memberUtil;

    public EntityController(Session session) {
        this.session = session;
    }

    public void setAccountUtil(EntityUtil<Account> accountUtil) {
        this.accountUtil = accountUtil;
    }

    public void setAlbumUtil(EntityUtil<Album> albumUtil) {
        this.albumUtil = albumUtil;
    }

    public void setGenreUtil(EntityUtil<Genre> genreUtil) {
        this.genreUtil = genreUtil;
    }

    public void setGroupUtil(EntityUtil<Group> groupUtil) {
        this.groupUtil = groupUtil;
    }

    public void setSongUtil(EntityUtil<Song> songUtil) {
        this.songUtil = songUtil;
    }

    public void createAlbum(Album album) throws Exception {
        albumUtil.createEntity(album);
    }

    public void createGenre(Genre genre) throws Exception {
        genreUtil.createEntity(genre);
    }

    public List<Album> getAlbums() {
        return albumUtil.getEntities();
    }

    public List<Account> getAccounts() {
        return accountUtil.getEntities();
    }

    public List<Genre> getGenres() {
        return genreUtil.getEntities();
    }

    public List<Member> getMembers() {
        return memberUtil.getEntities();
    }

    public List<Song> getSongs() {
        return songUtil.getEntities();
    }

    public List<Group> getGroups() {
        return groupUtil.getEntities();
    }

    public void setMemberUtil(EntityUtil<Member> memberUtil) {
        this.memberUtil = memberUtil;
    }

    public void createSong(Song song) {
        songUtil.createEntity(song);
    }

    public void createAccount(Account account) {
        accountUtil.createEntity(account);
    }

    public void createGroup(Group group) {
        groupUtil.createEntity(group);
    }

    public void createMember(Member member) {
        memberUtil.createEntity(member);
    }


    public void deleteMember(Integer id) throws Exception {
        memberUtil.deleteEntity(id);
    }

    public void deleteGenre(int id) throws Exception {
        genreUtil.deleteEntity(id);
    }

    public void deleteAccount(int id) throws Exception {
        accountUtil.deleteEntity(id);
    }

    public void deleteGroup(int id) throws Exception {
        groupUtil.deleteEntity(id);
    }

    public void deleteAlbum(int id) throws Exception {
        albumUtil.deleteEntity(id);
    }

    public void deleteSong(int id) throws Exception {
        songUtil.deleteEntity(id);
    }

    public void updateSong(Song oldSong, Song newSong) throws IllegalAccessException {
        songUtil.updateEntity(oldSong, newSong);
    }

    public Song getSong(int id) {
        return songUtil.getEntity(id);
    }

    public Account getAccount(int id) {
        return accountUtil.getEntity(id);
    }

    public void updateAccount(Account oldAccount, Account newAccount) throws IllegalAccessException {
        accountUtil.updateEntity(oldAccount, newAccount);
    }

    public Genre getGenre(int id) {
        return genreUtil.getEntity(id);
    }

    public void updateGenre(Genre oldGenre, Genre newGenre) throws IllegalAccessException {
        genreUtil.updateEntity(oldGenre, newGenre);
    }

    public Group getGroup(int id) {
        return groupUtil.getEntity(id);
    }

    public void updateGroup(Group oldGroup, Group newGroup) throws IllegalAccessException {
        groupUtil.updateEntity(oldGroup, newGroup);
    }

    public Member getMember(int id) {
        return memberUtil.getEntity(id);
    }

    public void updateMember(Member oldMember, Member newMember) throws IllegalAccessException {
        memberUtil.updateEntity(oldMember, newMember);
    }

    public Album getAlbum(int id) {
        return albumUtil.getEntity(id);
    }

    public void updateAlbum(Album oldAlbum, Album newAlbum) throws IllegalAccessException {
        albumUtil.updateEntity(oldAlbum, newAlbum);
    }

    public String getSongSizeSum() {
        return String.valueOf(this.session.createQuery("SELECT SUM(song.size) FROM Song song").list().get(0));
    }
}