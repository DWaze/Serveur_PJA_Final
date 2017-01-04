package com.example.lhadj.pja_final;

import java.io.Serializable;

/**
 * Created by lhadj on 12/25/2016.
 */
public class ListFile implements Serializable {
    //class that describes the list of file and all the information that we need
    public String getFileName() {
        return fileName;
    }
    public String getOwnerIp() {
        return ownerIp;
    }

    public void setFileName(String fileName) {

        this.fileName = fileName;
    }

    public void setOwnerIp(String ownerIp) {
        this.ownerIp = ownerIp;
    }

    private String fileName ;
    private String ownerIp ;

    public ListFile(String fileName, String ownerIp) {
        this.fileName = fileName;
        this.ownerIp = ownerIp;
    }
}
