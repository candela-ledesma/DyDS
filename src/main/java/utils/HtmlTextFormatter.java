package utils;

public class HtmlTextFormatter {

    public static String textToHtmlWithImage(String body, String imageUrl) {

        StringBuilder builder = new StringBuilder();

        builder.append("<html><body>");

        if (imageUrl != null) {
            builder.append("<img src='").append(imageUrl).append("' width='200' height='200'><br>");
        }

        String fixedText = body
                .replace("'", "`");

        builder.append(fixedText);

        builder.append("</body></html>");

        return builder.toString();
    }

    public static String textToHtmlWithHyperlink(String text, String hyperlink) {

        StringBuilder builder = new StringBuilder();

        builder.append("<html><body>");

        String fixedText = text
                .replace("'", "`");

        builder.append(fixedText);

        builder.append("<br>");

        builder.append("<a href=\"").append(hyperlink).append("\">").append(hyperlink).append("</a>");

        builder.append("</body></html>");

        return builder.toString();
    }

    public static String textToHtmlWithImageAndHyperLink(String body, String imageUrl, String hyperlink) {

        StringBuilder builder = new StringBuilder();

        builder.append("<html><body>");

        if (imageUrl != null) {
            builder.append("<img src='").append(imageUrl).append("' width='200' height='200'><br>");
        }

        String fixedText = body
                .replace("'", "`");

        builder.append(fixedText);

        builder.append("<br>");

        builder.append("<a href=\"").append(hyperlink).append("\">").append(hyperlink).append("</a>");

        builder.append("</body></html>");

        return builder.toString();
    }

    public static String formatContent(String extract, String imageUrl, String wikiUrl) {
        if (imageUrl != null && wikiUrl != null) {
            return textToHtmlWithImageAndHyperLink(extract, imageUrl, wikiUrl);
        } else if (imageUrl != null) {
            return textToHtmlWithImage(extract, imageUrl);
        } else if (wikiUrl != null) {
            return textToHtmlWithHyperlink(extract, wikiUrl);
        } else {
            return extract;
        }
    }


}
