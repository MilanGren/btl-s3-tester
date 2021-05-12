package cz.isfgroup.visitor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;

public class ImportStrategyV2 extends AbstractImportStrategy {

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attr) {

        setShift(dir);

        try {
            if (dir.getNameCount() > 2 + shift) {
                messages.put(dir, "unexpected directory");
                return CONTINUE;
            }
            if (dirIsEmpty(dir)) {
                messages.put(dir, "empty directory");
                return CONTINUE;
            }
        } catch (IOException e) {
            messages.put(dir, "exception: " + e.getMessage());
        }
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file,
                                     BasicFileAttributes attr) {
        if (file.getNameCount() < 2 + shift) {
            messages.put(file, "unexpected file");
            return CONTINUE;
        }
        if (attr.isSymbolicLink()) {
            messages.put(file, "is symbolic link");
            return CONTINUE;
        } else if (attr.isRegularFile()) {
            if (!isXml(file)) {
                messages.put(file, "has not .xml extension");
                return CONTINUE;
            }
        } else {
            messages.put(file, "is not regular file");
            return CONTINUE;
        }
        return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir,
                                              IOException exc) {
        if (exc != null) {
            messages.put(dir, "directory visit failed - " + exc.getMessage());
        }
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file,
                                           IOException exc) {
        messages.put(file, "file visit failed - " + exc.getMessage());
        return CONTINUE;
    }

}