package MyGUI;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Drawing canvas which inherits from JPanel
 */
public class DrawArea extends JPanel {
    private List<Dot> polygonMarker = new LinkedList<>();
    private List<AllShapes> history = new LinkedList<>(); //main history
    private List<String> vecHistory = new LinkedList<>();
    private AllShapes drag;
    private boolean dragging;

    /**
     * Gets history of all drawings in a List
     * @return a List that contains all instances of AllShapes to be drawn.
     */
    public List<AllShapes> getHistory() {
        return history;
    }

    /**
     * Sets the history of the panel to a List that contains all instances of AllShapes to be drawn.
     * @param history a List that contains all instances of AllShapes to be drawn
     */
    public void setHistory(List<AllShapes> history) {
        this.history = history;
    }


    /**
     * Boolean to track whether mouse is dragging on the drawing canvas.
     * @return true if it mouse is dragging on drawing canvas and false otherwise.
     */
    public boolean isDragging() {
        return dragging;
    }

    /**
     * Sets boolean to track whether mouse is dragging on the canvas.
     * @param dragging true if it mouse is dragging on drawing canvas and false otherwise.
     */
    void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    /**
     * Clears history of AllShapes List
     */
    public void clearHistory() {
        history.clear();
    }

    /**
     * Clears vecHistory
     */
    public void clearVEC() {
        vecHistory.clear();
    }


    /**
     * Gets string of all shapes in the canvas that is converted to vecHistory format
     * @return string of vecHistory commands separated by new line
     */
    public List<String> getAllVEC() {
        return vecHistory;
    }

    /**
     * Sets vecHistory as string
     * @param vecHistory List of vecHistory with each command as a string
     */
    public void setVecHistory(List<String> vecHistory) {
        this.vecHistory = vecHistory;
    }

    /**
     * Converts Color class colours to hex string which is used for vecHistory commands
     * @param colour Color class colour which will be converted to hex
     * @return string of hex value of the converted Color
     * @throws NullPointerException if invalid colour
     */
    public String toHexString(Color colour) throws NullPointerException {
        String hexColour = Integer.toHexString(colour.getRGB() & 0xffffff);
        if (hexColour.length() < 6) {
            hexColour = "000000".substring(0, 6 - hexColour.length()) + hexColour;
        }
        return "#" + hexColour;
    }

    /**
     * Adds shape into history and converts action into command to append into the stored vecHistory
     * @param shape AllShapes object that has to be added to the canvas
     * @param changedPEN boolean to know if pen colour changed
     * @param changedFILL boolean to know if fill colour changed
     * @param toggleFILL boolean to know if fill colour is removed
     */
    void addShape(AllShapes shape, boolean changedPEN, boolean changedFILL, boolean toggleFILL) {
        history.add(shape);
        String final_VEC ="";
        Color pen = shape.getColour();
        String hexPen = toHexString(pen);
        Color fill = shape.getFillColour();
        String hexFill = toHexString(fill);
        String shapeVEC = shape.getVEC();

        if (changedPEN){
            changedPEN = false;
            final_VEC = final_VEC.concat("PEN " + hexPen + ",");
        }
        if (changedFILL){
            changedFILL = false;
            final_VEC = final_VEC.concat("FILL " + hexFill + ",");
        }
        if (toggleFILL){
            toggleFILL = false;
            final_VEC = final_VEC.concat("FILL OFF" + ",");
        }

        final_VEC = final_VEC.concat(shapeVEC);
        vecHistory.add(final_VEC); //parameter - true if color changed
        this.repaint();
        }


    /**
     * Draws the shape while dragging
     * @param shape Shape that needs to be drawn while dragged
     */
    void dragLine(AllShapes shape){
        drag = shape;
        this.repaint();
    }

    /**
     * Adds to list to mark where the polygon edges will be at
     * @param poly Dot object which is added when user clicks to add polygon edges.
     */
    void addMarker(Dot poly) {
        polygonMarker.add(poly);
        this.repaint();
    }

    /**
     * Empties list so that no marks are left.
     */
    void clearMarker() {
        polygonMarker.clear();
    }

    /**
     * Gets list of markers to know number of edges
     * @return a List of Dots
     */
    public List<Dot> getPolygonMarker() {
        return polygonMarker;
    }

    /**
     * Remove the last item from the history and vecHistory such that it is no longer drawn.
     */
    void undoHistory() {
        if (history.size() > 0) {
            history.remove(history.get(history.size()-1)); //Remove history
            vecHistory.remove(vecHistory.get(vecHistory.size()-1)); //Remove vecHistory
            this.repaint();
        }
    }


    /**
     * Paints each component and marker separately, and tracks whether to draw a dragged shape.
     * @param g Graphics g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (AllShapes eachShape: history){
            if (eachShape.isVisible()) eachShape.draw(g, this.getWidth(), this.getHeight());
        }
        for (Dot eachDot: polygonMarker){
            eachDot.draw(g, this.getWidth(), this.getHeight());
        }
        //         Draws temporary line if user drags line
        if (drag != null && dragging) {
            drag.draw(g, this.getWidth(), this.getHeight());
        }

    }

    /**
     * An instance of DrawArea which sets background to white
     */
    public DrawArea() {
        setBackground(Color.WHITE);
    }



}
