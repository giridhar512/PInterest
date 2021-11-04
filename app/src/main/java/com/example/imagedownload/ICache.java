package com.example.imagedownload;

public interface ICache {
    void store(String httpUrl, String cachePath);
    String get(String httpUrl);
}
