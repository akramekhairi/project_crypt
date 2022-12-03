package com.gmail.projectCrypt.newsPage;

import com.gmail.projectCrypt.ui.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.cryptocontrol.cryptonewsapi.CryptoControlApi;
import io.cryptocontrol.cryptonewsapi.models.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Route(value = "News", layout = MainLayout.class)
@PageTitle("News")
public class newsView extends VerticalLayout {
    //Initialize api object using key in order to make API calls
    private CryptoControlApi api = new CryptoControlApi("6ca9b0ccc7e64df918c21426e1c832b0");

    public newsView() {

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 title = new H1("Latest Cryptocurrency News");
        title.getElement().getStyle().set("color", "rgb(18, 202, 214)");
        add(title);

        H2 topNews = new H2("Top News");
        add(topNews);

        TextField searchBar = new TextField();
        searchBar.setPlaceholder("Search for Cryptocurrencies to view their news...");
        //change this for the current cryptocurrency being shown
        searchBar.setAutoselect(true);
        searchBar.setAutofocus(true);
        searchBar.setSizeFull();

        add(searchBar);

        AtomicReference<String> coinName = new AtomicReference<>("bitcoin");

        List<news> newsArticles = new ArrayList<>();

        //Store what you need into the ArrayList here
        api.getTopNewsByCoin(coinName.get(), new CryptoControlApi.OnResponseHandler<List<Article>>() {
            public void onSuccess(List<Article> body) {
                for (Article article : body) {
                    newsArticles.add(new news(article.getTitle(), article.getDescription(), article.getSourceDomain(), article.getUrl(),article.getThumbnail()));
                }
            }

            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });

        //Initialize the grid and load it into the view
        Grid<news> newsGrid = new Grid<>(news.class);
        newsGrid.setItems(newsArticles);
        //Adding an option where for every article if clicked it will display the url, body, source, and thumbnail
        newsGrid.setSelectionMode(Grid.SelectionMode.NONE);
        newsGrid.setItemDetailsRenderer(TemplateRenderer.<news>of(
                "<div class='details' style=''>" +
                        "<div> [[item.body]] <br> </br> Read more on [[item.source]] <br> </br> <a href = \"[[item.url]]\" target=\"_blank\"> [[item.url]] </a> <br> </br> <img src = \"[[item.thumbnail]]\" alt = \"thumbnail\">  </p> " +
                        "</div>" +
                        "</div>"
        )
                .withProperty("body", news::getBody)
                .withProperty("source", news::getSource)
                .withProperty("url", news::getUrl)
                .withProperty("thumbnail", news::getThumbnail)
                .withEventHandler("handleClick", news -> {
                    newsGrid.getDataProvider().refreshItem(news);
        }));

        newsGrid.setSizeFull();
        newsGrid.setHeightByRows(true);
        newsGrid.addThemeVariants(GridVariant.MATERIAL_COLUMN_DIVIDERS, GridVariant.LUMO_NO_BORDER);
        newsGrid.setColumns("title", "body", "source");
        //Adding the grid
        add(newsGrid);

        searchBar.addValueChangeListener(event -> updateNews(event,api,newsGrid,coinName));

    }

    public static Grid updateNews(TextField.ValueChangeEvent event, CryptoControlApi api, Grid newsGrid, AtomicReference<String> coinName){
        coinName.set(String.valueOf(event.getValue()).toLowerCase());
        List<news> newsArticle = new ArrayList<>();
        //Update the newsArticle list and the grid using the new coinName and make a new API call and load it into news objects.
        api.getTopNewsByCoin(coinName.get(), new CryptoControlApi.OnResponseHandler<List<Article>>() {
            public void onSuccess(List<Article> body) {
                for (Article article : body) {
                    newsArticle.add(new news(article.getTitle(), article.getDescription(), article.getSourceDomain(), article.getUrl(),article.getThumbnail()));
                }
            }

            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });

        newsGrid.setItems(newsArticle);
        return newsGrid;

    }

}
