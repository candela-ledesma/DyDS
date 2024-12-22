package utils;

public class HtmlTextFormatter {

    public static String textToHtml(String text) {

        StringBuilder builder = new StringBuilder();

        builder.append("<font face=\"arial\">");

        String fixedText = text
                .replace("'", "`");

        builder.append(fixedText);

        builder.append("</font>");

        return builder.toString();
    }

    public static String textToHtmlWithImage(String text, String imageUrl) {

        StringBuilder builder = new StringBuilder();

        builder.append("<html><body>");

        if (imageUrl != null) {
            builder.append("<img src='").append(imageUrl).append("' width='200' height='200'><br>");
        }

        String fixedText = text
                .replace("'", "`");

        builder.append(fixedText);

        builder.append("</body></html>");

        return builder.toString();
    }


}
