package org.primefaces.california.service.analysis;

public interface Analysis {
    String getName();
    Result process();

    interface Result {
    }
}
