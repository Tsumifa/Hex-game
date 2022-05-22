package src.graphics;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;


// Cette class convertie les informations stockées dans le fichier de config en objet des class correspondantes
public class StylesManager {
    
    // Cette fonction fait plus de 10 lignes mais je ne pense pas qu'il faille la diviser en deux sous fonctions
    // Cette fonction récupère la couleur sélectionnée dans le fichier settings.json et instancie l'objet couleur correspondant
    public Color convertToColor( String colorName ) {

        switch ( colorName ) {

            case "RED":         return Color.RED;
            case "BLUE":        return Color.BLUE;
            case "GREEN":       return Color.GREEN;
            case "YELLOW":      return Color.YELLOW;
            case "WHITE":       return Color.WHITE;
            case "PINK":        return Color.PINK;
            case "ORANGE":      return Color.ORANGE;
            case "CYAN":        return Color.CYAN;
            case "DARK_GRAY":   return Color.DARK_GRAY;
            case "GRAY":        return Color.GRAY;
            case "LIGHT_GRAY":  return Color.LIGHT_GRAY;
            case "MAGENTA":     return Color.MAGENTA;
            default:            return Color.BLACK;

        }

    }

    public int convertToFont( String fontName ) {

        switch ( fontName ) {

            case "ITALIC":          return Font.ITALIC;
            case "BOLD":            return Font.BOLD;
            case "BOLD ITALIC":     return Font.BOLD + Font.ITALIC;
            default:                return Font.PLAIN;

        }

    }

    public String convertToBorderLayoutPosition( String position ) {

        switch ( position ) {

            case "NORTH":       return BorderLayout.NORTH;
            case "SOUTH":       return BorderLayout.SOUTH;
            case "EAST":        return BorderLayout.EAST;
            case "WEST":        return BorderLayout.WEST;
            default:            return BorderLayout.CENTER;

        }

    }

    public int convertToLabelAlignment( String alignment ) {

        switch ( alignment ) {

            case "LEFT":    return JLabel.LEFT;
            case "RIGHT":   return JLabel.RIGHT;
            default:        return JLabel.CENTER;

        }

    }
}
