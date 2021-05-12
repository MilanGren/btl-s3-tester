package cz.isfgroup.visitor;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractImportStrategy extends SimpleFileVisitor<Path> {

    protected Map<Path, String> messages = new HashMap<>();
    protected List<File> files = new ArrayList<>();
    protected boolean rootDirVisited = false;
    protected int shift = 0;

    protected Map<Path, String> validationMessages = new HashMap<>();

    protected Map<Path, String> get() {
        return validationMessages;
    }

    protected boolean isXml(Path file) {
        return file.toString().endsWith(".xml");
    }

    protected boolean dirIsEmpty(Path dir) throws IOException {
        return !Files.list(dir).findAny().isPresent();
    }

    protected long filesCountInDir(Path dir) throws IOException {
        return Files.list(dir).filter(Files::isRegularFile).count();
    }

    protected boolean containsGivenName(Path path, String expectedString, int ind) {
        File f = path.toFile().getParentFile();
        while (f != null) {
            if (expectedString.equals(f.getName()) && f.toPath().getNameCount() == ind) {
                System.out.println("PATH: " + path + "contains " + expectedString + ", index: " + f.toPath().getNameCount());
                return true;
            }
            f = f.getParentFile();
        }
        return false;
    }

    protected void setShift(Path dir) {
        if (!rootDirVisited) {
            shift = dir.getNameCount() - 1;
            rootDirVisited = true;
        }
    }
}
