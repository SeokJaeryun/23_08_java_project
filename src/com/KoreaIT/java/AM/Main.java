package com.KoreaIT.java.AM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    System.out.println("== 프로그램 시작 ==");
    Scanner sc = new Scanner(System.in);

    int lastArticleId = 0;
    List<Article> articles = new ArrayList<Article>();

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
      if (cmd.equals("make test data")){
        Article article = new Article();
        article.makeTestData(articles);
        continue;
      }
      if (cmd.equals("article write")) {
        String regDate = Util.getNowDateStr();
        int id = lastArticleId + 1;
        lastArticleId = id;

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
          for (int i = articles.size() - 1; i >= 0; i--) {
            Article article = articles.get(i);
            System.out.printf("%d  |  %s\n", article.id, article.title);
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
        System.out.printf("번호 : %d\n날짜 : %s\n제목 : %s\n내용 : %s\n", foundArticle.id, foundArticle.regDate, foundArticle.title, foundArticle.body);

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
        System.out.printf("제목 : ");
        String title = sc.nextLine();
        System.out.printf("내용 : ");
        String body = sc.nextLine();
        String regDate = Util.getNowDateStr();

        Article article1 = new Article(id, regDate, title, body);
        articles.set(foundidx, article1);
        System.out.printf("%d번 게시글이 수정 되었습니다.\n", id);
      } else {
        System.out.println("존재하지 않는 명령어 입니다.");
        continue;
      }
    }
    sc.close();
    System.out.println("== 프로그램 종료 ==");
  }
}

class Article {
  int id;
  String regDate;
  String title;
  String body;

  Article(int id, String regDate, String title, String body) {
    this.id = id;
    this.regDate = regDate;
    this.title = title;
    this.body = body;
  }

  public Article() {
  }

  void makeTestData(List articles){
    System.out.println("테스트 데이터를 생성합니다.");
    for (int i = 1; i<=3; i++){
      id = i;
      regDate = Util.getNowDateStr();
      title = "아무거나";
      body = "아무거나";
      Article article = new Article(id, regDate, title, body);
      articles.add(article);
    }
  }
}