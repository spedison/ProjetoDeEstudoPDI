package br.com.spedison.biblioteca_pdi_javafx.gui;

import br.com.spedison.biblioteca_pdi_javafx.conversores.ConversorSwingEJavaFX;
import br.com.spedison.biblioteca_pdi.base.Imagem;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ExibidorDeImagens extends Application {

    public static List<DadosDeImagemDaJanela> listaExplorarImagens = new ArrayList<>();

    public static int contaJanelas = 0;

    private static final int MIN_PIXELS = 5;

    @Override
    public void start(Stage primaryStage) {
        montaJanela(primaryStage, listaExplorarImagens.get(0).nomeJanela, listaExplorarImagens.get(0).image);
        for (int i = 1; i < listaExplorarImagens.size(); i++)
            montaJanela(new Stage(), listaExplorarImagens.get(i).nomeJanela, listaExplorarImagens.get(i).image);
    }


    public Stage montaJanela(Stage primaryStage, String nomeJanela, Image imagem) {

        Image image = imagem;
        double width = image.getWidth();
        double height = image.getHeight();

        contaJanelas++;
        primaryStage.setTitle(nomeJanela);
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        reset(imageView, width / 2, height / 2);

        ObjectProperty<Point2D> mouseDown = new SimpleObjectProperty<>();

        imageView.setOnMousePressed(e -> {
            Point2D mousePress = imageViewParaImagem(imageView, new Point2D(e.getX(), e.getY()));
            mouseDown.set(mousePress);
        });

        imageView.setOnMouseDragged(e -> {
            Point2D dragPoint = imageViewParaImagem(imageView, new Point2D(e.getX(), e.getY()));
            movimentaImagem(imageView, dragPoint.subtract(mouseDown.get()));
            mouseDown.set(imageViewParaImagem(imageView, new Point2D(e.getX(), e.getY())));
        });

        imageView.setOnScroll(e -> {
            double delta = e.getDeltaY();
            Rectangle2D viewport = imageView.getViewport();

            double scale = limitaValores(Math.pow(1.01, delta),

                    // don't scale so we're zoomed in to fewer than MIN_PIXELS in any direction:
                    Math.min(MIN_PIXELS / viewport.getWidth(), MIN_PIXELS / viewport.getHeight()),

                    // don't scale so that we're bigger than image dimensions:
                    Math.max(width / viewport.getWidth(), height / viewport.getHeight())

            );

            Point2D mouse = imageViewParaImagem(imageView, new Point2D(e.getX(), e.getY()));

            double newWidth = viewport.getWidth() * scale;
            double newHeight = viewport.getHeight() * scale;

            // To keep the visual point under the mouse from moving, we need
            // (x - newViewportMinX) / (x - currentViewportMinX) = scale
            // where x is the mouse X coordinate in the image

            // solving this for newViewportMinX gives

            // newViewportMinX = x - (x - currentViewportMinX) * scale

            // we then clamp this value so the image never scrolls out
            // of the imageview:

            double newMinX = limitaValores(mouse.getX() - (mouse.getX() - viewport.getMinX()) * scale,
                    0, width - newWidth);
            double newMinY = limitaValores(mouse.getY() - (mouse.getY() - viewport.getMinY()) * scale,
                    0, height - newHeight);

            imageView.setViewport(new Rectangle2D(newMinX, newMinY, newWidth, newHeight));
        });

        imageView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                reset(imageView, width, height);
            }
        });

        HBox buttons = criaBotoes(width, height, imageView);
        Tooltip tooltip = new Tooltip("Scroll para aumentar ou reduzir a imagem, click e arraste para movimentar.");
        Tooltip.install(buttons, tooltip);

        Pane container = new Pane(imageView);
        container.setPrefSize(800, 600);

        imageView.fitWidthProperty().bind(container.widthProperty());
        imageView.fitHeightProperty().bind(container.heightProperty());
        VBox root = new VBox(container, buttons);
        root.setFillWidth(true);
        root.setManaged(true);
        root.setSpacing(10);
        root.paddingProperty().set(new Insets(10, 50, 0, 50));
        VBox.setVgrow(container, Priority.ALWAYS);

        primaryStage.setScene(new Scene(root));
        // primaryStage.setTitle("Visualizador de Imagens");
        primaryStage.show();

        return primaryStage;
    }

    private HBox criaBotoes(double width, double height, ImageView imageView) {
        Button reset = new Button("Visão Inicial");
        reset.setOnAction(e -> reset(imageView, width / 2, height / 2));
        Button full = new Button("Visão Completa");
        full.setOnAction(e -> reset(imageView, width, height));

        Button saida = new Button("Sair");
        saida.setOnAction(e -> Platform.exit());

        HBox buttons = new HBox(10, reset, full, saida);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(10));
        return buttons;
    }

    // reset to the top left:
    private void reset(ImageView imageView, double width, double height) {
        imageView.setViewport(new Rectangle2D(0, 0, width, height));
    }

    // shift the viewport of the imageView by the specified delta, clamping so
    // the viewport does not move off the actual image:
    private void movimentaImagem(ImageView imageView, Point2D delta) {
        Rectangle2D viewport = imageView.getViewport();

        double width = imageView.getImage().getWidth();
        double height = imageView.getImage().getHeight();

        double maxX = width - viewport.getWidth();
        double maxY = height - viewport.getHeight();

        double minX = limitaValores(viewport.getMinX() - delta.getX(), 0, maxX);
        double minY = limitaValores(viewport.getMinY() - delta.getY(), 0, maxY);

        imageView.setViewport(new Rectangle2D(minX, minY, viewport.getWidth(), viewport.getHeight()));
    }

    private double limitaValores(double value, double min, double max) {

        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    // convert mouse coordinates in the imageView to coordinates in the actual image:
    private Point2D imageViewParaImagem(ImageView imageView, Point2D imageViewCoordinates) {
        double xProportion = imageViewCoordinates.getX() / imageView.getBoundsInLocal().getWidth();
        double yProportion = imageViewCoordinates.getY() / imageView.getBoundsInLocal().getHeight();

        Rectangle2D viewport = imageView.getViewport();
        return new Point2D(
                viewport.getMinX() + xProportion * viewport.getWidth(),
                viewport.getMinY() + yProportion * viewport.getHeight());
    }

    public static void adicionaImagem(String titulo, Imagem imagem){
        DadosDeImagemDaJanela dj = new DadosDeImagemDaJanela(titulo, ConversorSwingEJavaFX.converteParaImage(imagem.getImageBuffer()));
        listaExplorarImagens.add(dj);
    }

    public static void mostraJanelas(){
        launch();
    }
}