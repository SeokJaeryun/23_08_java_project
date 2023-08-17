package com.KoreaIT.java.AM;

import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.dto.Member;
import com.KoreaIT.java.AM.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
  private List<Article> articles;
  private List<Member> members;

  App() {
    articles = new ArrayList<>();
    members = new ArrayList<>();
  }


  public void start() {
    System.out.println("== 프로그램 시작 ==");
    makeTestData();
    Scanner sc = new Scanner(System.in);


    while (true) {
      System.out.printf("명령어 )");
      String cmd = sc.nextLine().trim();
      if (cmd.length() == 0) {
        System.out.println("명령어를 입력하세요");
        continue;
      }
      if (cmd.equals("system exit")) {
        break;
      }

      if (cmd.equals("member join")) {
        int id = members.size() + 1;


        String regDate = Util.getNowDateStr();
        System.out.printf("로그인 아이디 : ");
        String loginID = sc.nextLine();


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


      if (cmd.equals("article write")) {
        String regDate = Util.getNowDateStr();
        int id = articles.size() + 1;

        System.out.printf("제목 : ");
        String title = sc.nextLine();
        System.out.printf("내용 : ");
        String body = sc.nextLine();

        Article article = new Article(id, regDate, title, body);
        articles.add(article);

        System.out.printf("%d번글이 생성 되었습니다.\n", id);

      } else if (cmd.startsWith("article list")) {
        if (articles.isEmpty()) {
          System.out.println("게시글이 없습니다.");
          continue;
        } else {
          String searchKeyword = cmd.substring("article list".length()).trim();
          System.out.printf("검색어 : %s\n", searchKeyword);

          List<Article> forPrintArticles = articles;
          if (searchKeyword.length() > 0) {
            forPrintArticles = new ArrayList<>();

            for (Article article : articles) {
              if (article.title.contains(searchKeyword)) {
                forPrintArticles.add(article);
              }
            }
            if (forPrintArticles.size() == 0) {
              System.out.println("검색결과가 없습니다");
              continue;
            }
          }
          System.out.println("번호    |    제목    |    조회수");
          for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
            Article article = forPrintArticles.get(i);
            System.out.printf("%2d      |    %2s    |      %3d\n", article.id, article.title, article.viewCnt);
          }
        }

      } else if (cmd.startsWith("article detail ")) {
        String[] cmdBits = cmd.split(" ");
        int id = Integer.parseInt(cmdBits[2]);
        Article foundArticle = getArticleByid(id);
        if (foundArticle == null) {
          System.out.printf("%d번 게시글이 존재하지 않습니다.\n", id);
          continue;
        }
        foundArticle.increaseViewCnt();
        System.out.printf("번호 : %d\n날짜 : %s\n제목 : %s\n내용 : %s\n조회수 : %d\n", foundArticle.id, foundArticle.regDate, foundArticle.title, foundArticle.body, foundArticle.viewCnt);

      } else if (cmd.startsWith("article delete ")) {
        String[] cmdBits = cmd.split(" ");
        int id = Integer.parseInt(cmdBits[2]);

        int foundidx = getidxByid(id);
        if (foundidx == -1) {
          System.out.printf("%d번 게시글이 존재하지 않습니다.\n", id);
          continue;
        }
        articles.remove(foundidx);
        System.out.printf("%d번 게시글이 삭제 되었습니다.\n", id);

      } else if (cmd.startsWith("article modify ")) {
        String[] cmdBits = cmd.split(" ");
        int id = Integer.parseInt(cmdBits[2]);

        Article foundArticle = getArticleByid(id);

        if (foundArticle == null) {
          System.out.printf("%d번 게시글이 존재하지 않습니다.\n", id);
          continue;
        }
        System.out.printf("제목 : ");
        String title = sc.nextLine();
        System.out.printf("내용 : ");
        String body = sc.nextLine();
        String regDate = Util.getNowDateStr();
        foundArticle.title = title;
        foundArticle.body = body;
        foundArticle.regDate = regDate;

        System.out.printf("%d번 게시글이 수정 되었습니다.\n", id);
      } else {
        System.out.println("존재하지 않는 명령어 입니다.");
        continue;
      }
    }
    sc.close();
    System.out.println("== 프로그램 종료 ==");
  }

  private void makeTestData() {
    System.out.println("테스트데이터를 생성합니다.");
    articles.add(new Article(1, Util.getNowDateStr(), "title1", "body1", 11));
    articles.add(new Article(2, Util.getNowDateStr(), "title2", "body2", 22));
    articles.add(new Article(3, Util.getNowDateStr(), "title3", "body3", 33));
  }

  private int getidxByid(int id) {
    int i = 0;
    for (Article article : articles) {
      if (article.id == id) {
        return i;
      }
      i++;
    }
    return -1;
  }

  private Article getArticleByid(int id) {
    int index = getidxByid(id);
    if (index != -1) {
      return articles.get(index);
    }
    return null;
  }
}
