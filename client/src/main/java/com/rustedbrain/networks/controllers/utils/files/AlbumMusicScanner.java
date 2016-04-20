package com.rustedbrain.networks.controllers.utils.files;

import com.rustedbrain.networks.model.music.Song;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by RustedBrain on 13.01.2016.
 */
public class AlbumMusicScanner {

    private static int id;
    private List<Song> songs = new ArrayList<>();
    private Pattern pattern = Pattern.compile("^([0-9]+( )?(. )?( - )?)");

    public static void main(String[] args) throws IOException {
        AlbumMusicScanner scanner = new AlbumMusicScanner();
        scanner.walkFiles("D:\\Media");
        System.out.println(scanner.getSongs());
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void walkFiles(String directoryPath) throws IOException {

        Files.walkFileTree(Paths.get(directoryPath), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String fileName = file.getFileName().toString();
                if (fileName.endsWith(".flac") || fileName.endsWith(".mp3")
                        || fileName.endsWith(".m4a") || fileName.endsWith(".ogg")
                        || fileName.endsWith(".m3u"))
                    try {
                        addSong(file.toFile());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                return FileVisitResult.CONTINUE;
            }
        });
    }

    public void addSong(File file) throws Exception {
        Matcher matcher = pattern.matcher(file.getName());
        String fileName;
        if (matcher.find())
            fileName = file.getName().substring(matcher.end(), file.getName().lastIndexOf("."));
        else
            fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
        Song song = new Song();
        song.setId(this.id++);
        song.setName(fileName);
        song.setSize((double) (file.length() / (1024 * 1024)));
        song.setPath(file.getAbsolutePath());
        song.setCreation(new Date(System.currentTimeMillis()));
        songs.add(song);
    }
}
