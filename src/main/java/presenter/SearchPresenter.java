package presenter;

import model.MainModel;
import model.Serie;
import view.MainView;
import view.SearchPanel;
import view.SerieMenuItem;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.function.BiConsumer;

import static utils.HtmlTextFormatter.*;

public class SearchPresenter {
    private final MainView view;
    private final MainModel model;

    public SearchPresenter(MainView view, MainModel model) {
        this.view = view;
        this.model = model;
    }

    public void searchSeries() {
        new Thread(() -> {
            if(view.getSearchSerieField().isEmpty()) {
                view.showErrorMessage("Please enter a series name");
                return;
            }
            view.showResults(getListOfSeries());
        }).start();
    }

    public void searchSeriesImage() {
        new Thread(() -> {
            if(view.getSearchSerieField().isEmpty()){
                view.showErrorMessage("Please enter a series name");
                return;
            }
            view.showResultsImage(getListOfSeries());
        }).start();
    }

    private LinkedList<Serie> getListOfSeries() {
        String seriesName = view.getSearchSerieField();
        LinkedList<Serie> results = null;
        try {
            results = model.searchSeries(seriesName);
        } catch (IOException e) {
            view.showErrorMessage(e.getMessage());
        }
        return results;
    }

    public void getSelectedExtract(Serie selectedResult) throws SQLException {
        String url = selectedResult.getUrl();
        String extract = handleExtract(selectedResult);
        view.setSearchResultTextPane(textToHtmlWithHyperlink(extract, url));
    }

    public void getSelectedExtractImage(Serie searchResult) throws SQLException {
        String wikiUrl = searchResult.getUrl();
        String extract = handleExtract(searchResult);
        String imageUrl = null;
        try {
            imageUrl = model.getPageImageUrl(searchResult);
        } catch (IOException e) {
            view.showErrorMessage(e.getMessage());
        }
        view.setSearchResultTextPane(textToHtmlWithImageAndHyperLink(extract, imageUrl, wikiUrl));
    }

    private String handleExtract(Serie result) {
        String extract = null;
        try {
            extract = model.searchPageExtract(result);
        } catch (IOException e) {
            view.showErrorMessage(e.getMessage());
        }
        return extract;
    }

    public void handleShowResults(LinkedList<Serie> results, JTextPane searchResultsTextPane) {
        handleShowResultsCommon(results, searchResultsTextPane, this::addMenuItem);
    }

    public void handleShowResultsImage(LinkedList<Serie> results, JTextPane searchResultsTextPane) {
        handleShowResultsCommon(results, searchResultsTextPane, this::addMenuItemImage);
    }

    private void handleShowResultsCommon(LinkedList<Serie> results, JTextPane searchResultsTextPane, BiConsumer<SerieMenuItem, Serie> action) {
        JPopupMenu searchOptionsMenu = new JPopupMenu("Search Results");

        for (Serie searchResult : results) {
            String title = searchResult.getTitle();
            boolean hasScore = model.hasScore(title);
            String displayTitle = hasScore ? "★ " + title : title;
            SerieMenuItem menuItem = new SerieMenuItem(displayTitle, searchResult.getSnippet());
            view.setMenuItem(menuItem);
            action.accept(menuItem, searchResult);
            searchOptionsMenu.add(menuItem);
        }
        searchOptionsMenu.show(searchResultsTextPane, searchResultsTextPane.getX(), searchResultsTextPane.getY());
    }

    private void addMenuItem(SerieMenuItem menuItem, Serie searchResult) {
        view.addMenuItem(menuItem, searchResult);
    }

    private void addMenuItemImage(SerieMenuItem menuItem, Serie searchResult) {
        view.addMenuItemImage(menuItem, searchResult);
    }

}
