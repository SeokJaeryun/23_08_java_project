package com.KoreaIT.java.AM.controller;

import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArticleController extends Controller {
  private List<Article> articles;
  private Scanner sc;
  private String cmd;
  private String actionMethodName;

  public ArticleController(Scanner sc) {
    this.articles = new ArrayList<Article>();
    this.sc = sc;
  }

  @Override
  public void doAction(String cmd, String actionMethodName) {
    this.cmd = cmd;
    this.actionMethodName = actionMethodName;

    switch (actionMethodName) {
      case "list":
        showList();
        break;
      case "detail":
        showDetail();
        break;
      case "write":
        doWrite();
        break;
      case "modify":
        doModify();
        break;
      case "delete":
        doDelete();
        break;
      default:
        System.out.println("존재하지 않는 명령어입니다.");
        break;
    }
  }

  private void doWrite() {
    String regDate = Util.getNowDateStr();
    int id = articles.size() + 1;

    System.out.printf("제목 : ");
    String title = sc.nextLine();
    System.out.printf("내용 : ");
    String body = sc.nextLine();

    Article article = new Article(id, regDate, title, body);
    articles.add(article);

    System.out.printf("%d번글이 생성 되었습니다.\n", id);
  }

  private void showDetail() {
    String[] cmdBits = cmd.split(" ");
    int id = Integer.parseInt(cmdBits[2]);
    Article foundArticle = getArticleByid(id);
    if (foundArticle == null) {
      System.out.printf("%d번 게시글이 존재하지 않습니다.\n", id);
      return;
    }
    foundArticle.increaseViewCnt();
    System.out.printf("번호 : %d\n날짜 : %s\n제목 : %s\n내용 : %s\n조회수 : %d\n", foundArticle.id, foundArticle.regDate, foundArticle.title, foundArticle.body, foundArticle.viewCnt);
  }

  private void showList() {
    if (articles.isEmpty()) {
      System.out.println("게시글이 없습니다.");
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
        if (forPrintArticles.isEmpty()) {
          System.out.println("검색결과가 없습니다");
        } else {
          System.out.println("번호    |    제목    |    조회수");
        }
      }
      for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
        Article article = forPrintArticles.get(i);
        System.out.printf("%2d      |    %2s    |      %3d\n", article.id, article.title, article.viewCnt);
      }
    }
  }

  private void doDelete() {
    String[] cmdBits = cmd.split(" ");
    int id = Integer.parseInt(cmdBits[2]);

    int foundidx = getidxByid(id);
    if (foundidx == -1) {
      System.out.printf("%d번 게시글이 존재하지 않습니다.\n", id);
      return;
    }
    articles.remove(foundidx);
    System.out.printf("%d번 게시글이 삭제 되었습니다.\n", id);
  }

  private void doModify() {
    String[] cmdBits = cmd.split(" ");
    int id = Integer.parseInt(cmdBits[2]);
    Article foundArticle = getArticleByid(id);

    if (foundArticle == null) {
      System.out.printf("%d번 게시글이 존재하지 않습니다.\n", id);
      return;
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

  public void makeTestData() {
    System.out.println("게시물 테스트데이터를 생성합니다.");
    articles.add(new Article(1, Util.getNowDateStr(), "title1", "body1", 11));
    articles.add(new Article(2, Util.getNowDateStr(), "title2", "body2", 22));
    articles.add(new Article(3, Util.getNowDateStr(), "title3", "body3", 33));
  }
}
