package com.archives.pojo;

public class ArcSeq {
    private Integer id;

    private String seqname;

    private Integer seqvalue;

    private String seqyear;

    private String seqgroup;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeqname() {
        return seqname;
    }

    public void setSeqname(String seqname) {
        this.seqname = seqname == null ? null : seqname.trim();
    }

    public Integer getSeqvalue() {
        return seqvalue;
    }

    public void setSeqvalue(Integer seqvalue) {
        this.seqvalue = seqvalue;
    }

    public String getSeqyear() {
        return seqyear;
    }

    public void setSeqyear(String seqyear) {
        this.seqyear = seqyear == null ? null : seqyear.trim();
    }

    public String getSeqgroup() {
        return seqgroup;
    }

    public void setSeqgroup(String seqgroup) {
        this.seqgroup = seqgroup == null ? null : seqgroup.trim();
    }
}