package com.teapps.tgsis;

public class Education {
    private EduType type;
    private String name;

    public Education(EduType type, String name) {
        this.type = type;
        this.name = name;
    }

    public EduType getType() {
        return type;
    }

    public void setType(EduType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

enum EduType{
    LISANS,YUKSEK_LISANS,DOKTORA
};