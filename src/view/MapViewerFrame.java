package view;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import java.awt.*;

public class MapViewerFrame extends JFrame {
    private JXMapViewer mapViewer;

    public MapViewerFrame() {
        setTitle("Map Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the map viewer
        mapViewer = new JXMapViewer();

        // Set the tile factory (OpenStreetMap provider)
        mapViewer.setTileFactory(new DefaultTileFactory(new OSMTileFactoryInfo()));

        // Set initial map position and zoom level
        mapViewer.setCenterPosition(new GeoPosition(40.7128, -74.0060)); // NYC
        mapViewer.setZoom(8);

        // Add zoom and pan mouse interaction
        mapViewer.addMouseListener(new PanMouseInputListener(mapViewer)); // Drag to pan
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer)); // Scroll to zoom

        // Add the map viewer to the frame
        add(mapViewer, BorderLayout.CENTER);

        setVisible(true); // Show the frame
    }

    public JXMapViewer getMapViewer() {
        return mapViewer;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MapViewerFrame::new); // Start the GUI on the event dispatch thread
    }
}