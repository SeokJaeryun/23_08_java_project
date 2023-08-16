package com.KoreaIT.java.AM;

import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
  private List<Article> articles;

  App() {
    articles = new ArrayList<>();
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

      } else if (cmd.equals("article list")) {
        if (articles.isEmpty()) {
          System.out.println("게시글이 없습니다.");
          continue;
        } else {
          System.out.println("번호    |    제목    |    조회수");
          for (int i = articles.size() - 1; i >= 0; i--) {
            Article article = articles.get(i);
            System.out.printf("%2d      |    %2s    |      %3d\n", article.id, article.title, article.viewCnt);
          }
        }
      } else if (cmd.startsWith("article detail ")) {
        String[] cmdBits = cmd.split(" ");
        int id = Integer.parseInt(cmdBits[2]);

        Article foundArticle = null;
        for (int i = 0; i < articles.size(); i++) {
          Article article = articles.get(i);
          if (article.id == id) {
            foundArticle = article;
            break;
          }
        }
        if (foundArticle == null) {
          System.out.printf("%d번 게시글이 존재하지 않습니다.\n", id);
          continue;
        }
        foundArticle.increaseViewCnt();
        System.out.printf("번호 : %d\n날짜 : %s\n제목 : %s\n내용 : %s\n조회수 : %d\n", foundArticle.id, foundArticle.regDate, foundArticle.title, foundArticle.body, foundArticle.viewCnt);
        continue;

      } else if (cmd.startsWith("article delete ")) {
        String[] cmdBits = cmd.split(" ");
        int id = Integer.parseInt(cmdBits[2]);

        int foundidx = -1;
        for (int i = 0; i < articles.size(); i++) {
          Article article = articles.get(i);
          if (article.id == id) {
            foundidx = i;
            break;
          }
        }
        if (foundidx == -1) {
          System.out.printf("%d번 게시글이 존재하지 않습니다.\n", id);
          continue;
        }
        articles.remove(foundidx);
        System.out.printf("%d번 게시글이 삭제 되었습니다.\n", id);
      } else if (cmd.startsWith("article modify ")) {
        String[] cmdBits = cmd.split(" ");
        int id = Integer.parseInt(cmdBits[2]);

        int foundidx = -1;
        Article foundArticle = null;
        for (int i = 0; i < articles.size(); i++) {
          Article article = articles.get(i);
          if (article.id == id) {
            foundidx = i;
            foundArticle = article;
            break;
          }
        }
        if (foundidx == -1) {
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
}
