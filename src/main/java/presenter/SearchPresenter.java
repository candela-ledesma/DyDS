package presenter;

import model.MainModel;
import model.Serie;
import view.MainView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

public class SearchPresenter {
    private final MainView view;
    private final MainModel model;

    public SearchPresenter(MainView view, MainModel model) {
        this.view = view;
        this.model = model;
    }

    public void searchSeries() {
        new Thread(() -> {
            view.showResults(getListOfSeries());
        }).start();
    }

    public void searchSeriesImage() {
        new Thread(() -> {
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
        String hyperlink = "<a href=\"" + url + "\">" + url + "</a>";
        view.setSearchResultTextPane(extract + "<br>" + hyperlink);
    }

    public void getSelectedExtractImage(Serie searchResult) {
        String extract = handleExtract(searchResult);
        String imageUrl = null;
        try {
            imageUrl = model.getPageImageUrl(searchResult);
        } catch (IOException e) {
            view.showErrorMessage(e.getMessage());
        }
        view.setSearchResultTextPaneImage(extract, imageUrl);
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
}
