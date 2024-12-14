package com.kyrsach.demon;

import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.UserPrincipal;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class FileInfo {
    private final String name;
    private final long size;
    private final LocalDateTime creationTime;
    private final String owner;
    private final boolean isDirectory;

    public FileInfo(String name, long size, FileTime creationTime, UserPrincipal owner, boolean isDirectory) {
        this.name = name;
        this.size = size;
        this.creationTime = LocalDateTime.ofInstant(
                creationTime.toInstant(),
                ZoneId.systemDefault()
        );
        this.owner = owner.getName();
        this.isDirectory = isDirectory;
    }

    @Override
    public String toString() {
        String sizeStr = isDirectory ? "" : String.format(" (%d bytes)", size);
        return String.format("%s%s - Created: %s - Owner: %s",
                name,
                sizeStr,
                creationTime.toString(),
                owner
        );
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public String getOwner() {
        return owner;
    }

    public boolean isDirectory() {
        return isDirectory;
    }
}