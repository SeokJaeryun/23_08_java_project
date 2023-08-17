package com.KoreaIT.java.AM.controller;

import com.KoreaIT.java.AM.dto.Member;
import com.KoreaIT.java.AM.util.Util;

import java.util.List;
import java.util.Scanner;


public class MemberController {
  private List<Member> members;
  private Scanner sc;
  public MemberController(List<Member> members, Scanner sc){
    this.sc =sc;
    this.members = members;
  }
  public void dojoin(){

    int id = members.size() + 1;

    String regDate = Util.getNowDateStr();
    String loginID = null;

    while (true) {
      System.out.printf("로그인 아이디 : ");
      loginID = sc.nextLine();
      if (isJoinableLoginId(loginID) == false) {
        System.out.printf("%s(은)는 이미 사용중인 아이디입니다.\n", loginID);
        continue;
      }
      break;
    }

    String loginPW = null;
    String loginPwCheck = null;
    while (true) {
      System.out.printf("로그인 비밀번호 : ");
      loginPW = sc.nextLine();
      System.out.printf("로그인 비밀번호 확인 : ");
      loginPwCheck = sc.nextLine();
      if (loginPwCheck.equals(loginPW) == false) {
        System.out.println("비밀번호를 다시 입력하세요.");
        continue;
      }
      break;
    }
    System.out.printf("이름 : ");
    String name = sc.nextLine();
    Member member = new Member(id, regDate, loginID, loginPW, name);
    members.add(member);
  }
  private boolean isJoinableLoginId(String loginID) {
    int index = getMemberIndexByloginId(loginID);
    if (index == -1){
      return true;
    }
    return false;
  }

  private int getMemberIndexByloginId(String loginID) {
    int i = 0;
    for (Member member : members){
      if(member.loginId.equals(loginID)){
        return i;
      }
      i++;
    }
    return -1;
  }
}
