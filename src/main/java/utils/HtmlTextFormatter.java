package utils;

public class HtmlTextFormatter {


    public static String textToHtmlWithImage(String body, String imageUrl) {
        StringBuilder builder = new StringBuilder();
        builder.append("<html><body>");

        if (imageUrl != null) {
            builder.append(formatImageTag(imageUrl));
        }

        builder.append(escapeText(body));
        builder.append("</body></html>");

        return builder.toString();
    }

    public static String textToHtmlWithHyperlink(String text, String hyperlink) {
        StringBuilder builder = new StringBuilder();
        builder.append("<html><body>");

        builder.append(escapeText(text));
        builder.append("<br>");
        builder.append(formatHyperlinkTag(hyperlink));

        builder.append("</body></html>");

        return builder.toString();
    }

    public static String textToHtmlWithImageAndHyperLink(String body, String imageUrl, String hyperlink) {
        StringBuilder builder = new StringBuilder();
        builder.append("<html><body>");

        if (imageUrl != null) {
            builder.append(formatImageTag(imageUrl));
        }

        builder.append(escapeText(body));
        builder.append("<br>");
        builder.append(formatHyperlinkTag(hyperlink));

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
            return escapeText(extract);
        }
    }


    private static String escapeText(String text) {
        return text.replace("'", "`");
    }


    private static String formatImageTag(String imageUrl) {
        return "<img src='" + imageUrl + "' width='200' height='200'><br>";
    }


    private static String formatHyperlinkTag(String hyperlink) {
        return "<a href=\"" + hyperlink + "\">" + hyperlink + "</a>";
    }
}
