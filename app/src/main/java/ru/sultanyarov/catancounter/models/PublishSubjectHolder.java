package ru.sultanyarov.catancounter.models;

import io.reactivex.subjects.PublishSubject;

public class PublishSubjectHolder {
    private static volatile PublishSubjectHolder instance;
    public final PublishSubject<Player> players = PublishSubject.create();

    public static PublishSubjectHolder getInstance() {
        PublishSubjectHolder localInstance = instance;
        if (localInstance == null) {
            synchronized (PublishSubjectHolder.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new PublishSubjectHolder();
                }
            }
        }
        return localInstance;
    }
}
