package org.example.day0819_board_project.domain;

public class Member {

    private Long id;

    private String memberId;

    private String password;

    public Member(Long id, String memberId, String password) {
        this.id = id;
        this.memberId = memberId;
        this.password = password;
    }

    public Member(String memberId, String password) {
        this.memberId = memberId;
        this.password = password;
    }

    public Member() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
