package br.com.spedison.biblioteca_pdi_javafx.gui;
/*
import br.com.spedison.biblioteca_pdi.base.Imagem;
import br.com.spedison.biblioteca_pdi_javafx.conversores.ConversorSwingEJavaFX;
import javafx.animation.Interpolatable;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
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
import java.util.Collections;
import java.util.List;

public class ExibidorListaDeImagensTemporizado extends Application implements Runnable {

    public static List<DadosDeImagemDaJanela> listaDeImagens = new ArrayList<>();
    private static int imagemAtual = 0;

    private static boolean podeRodar = true;

    private static final int MIN_PIXELS = 5;

    private static ImageView imageView = new ImageView();

    private static Stage primaryStage = null;

    protected static void ordenaPorNomeJanela() {
        Collections.sort(listaDeImagens);
    }

    @Override
    public void start(Stage primaryStage) {
        montaJanela(primaryStage);
    }

    @Override
    public void run() {
        while (podeRodar) {
            imagemAtual++;
            imagemAtual = imagemAtual % listaDeImagens.size();
            Platform.runLater(() -> {
                        this.atualizaImagem(imagemAtual);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ie) {
                        }
                    }
            );
        }
    }

    public void atualizaImagem(int imagemAtual) {

        Image img = listaDeImagens.get(imagemAtual).image;
        //Bindings.bindContent(imageView.imageProperty(), img);

        primaryStage.setTitle(listaDeImagens.get(imagemAtual).nomeJanela);
        // imageView.setImage(img); // new ImageView(image);
        imageView.setPreserveRatio(true);
        reset(imageView, img.getWidth(), img.getHeight());
    }

    public Stage montaJanela(Stage primaryStage) {

        ExibidorListaDeImagensTemporizado.primaryStage = primaryStage;

        atualizaImagem(0);

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
                    Math.max(imageView.getImage().getWidth() / viewport.getWidth(), imageView.getImage().getHeight() / viewport.getHeight())
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
                    0, imageView.getImage().getWidth() - newWidth);
            double newMinY = limitaValores(mouse.getY() - (mouse.getY() - viewport.getMinY()) * scale,
                    0, imageView.getImage().getHeight() - newHeight);

            imageView.setViewport(new Rectangle2D(newMinX, newMinY, newWidth, newHeight));
        });

        imageView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                reset(imageView, imageView.getImage().getWidth(), imageView.getImage().getHeight());
            }
        });

        HBox buttons = criaBotoes();
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
        primaryStage.show();

        return primaryStage;
    }

    private HBox criaBotoes() {
        Button reset = new Button("Visão Inicial");
        reset.setOnAction(e -> reset(imageView, imageView.getImage().getWidth() / 2, imageView.getImage().getHeight() / 2));

        Button full = new Button("Visão Completa");
        full.setOnAction(e -> reset(imageView, imageView.getImage().getWidth(), imageView.getImage().getHeight()));

        Button proximaImagem = new Button("Parar");//róxima Imagem");
        proximaImagem.setOnAction(e -> {
            podeRodar = false;
            //imagemAtual++;
            //imagemAtual %= listaDeImagens.size();
            //atualizaImagem(imagemAtual);
        });

        Button imagemAnterior = new Button("Iniciar"); //magem Anterior");

        imagemAnterior.setOnAction(e -> {
            Task task = new Task() {
                @Override
                protected Object call() throws Exception {
                    while (podeRodar) {
                        imagemAtual++;
                        imagemAtual = imagemAtual % listaDeImagens.size();
                        Platform.runLater(() -> {
                            atualizaImagem(imagemAtual);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ie) {
                            }
                        });
                    }
                    return null;
                }
            };
            new Thread(task).start();


            //imagemAtual--;
            //imagemAtual = imagemAtual < 0 ? listaDeImagens.size() - 1 : imagemAtual;
            //atualizaImagem(imagemAtual);
        });

        Button saida = new Button("Sair");
        saida.setOnAction(e -> Platform.exit());

        HBox buttons = new HBox(10, reset, full, imagemAnterior, proximaImagem, saida);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new

                Insets(10));
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

    protected static synchronized void adicionaImagem(String titulo, Imagem imagem) {
        DadosDeImagemDaJanela dj = new DadosDeImagemDaJanela(titulo, ConversorSwingEJavaFX.converteParaImage(imagem.getImageBuffer()));
        listaDeImagens.add(dj);
    }

    protected static void mostraJanela() {
        launch();
    }
}
*/
