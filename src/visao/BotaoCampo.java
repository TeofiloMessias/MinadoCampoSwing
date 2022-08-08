package visao;

import modelo.Campo;
import modelo.CampoEvento;
import modelo.CampoObservador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@SuppressWarnings("serial")
public class BotaoCampo extends JButton implements CampoObservador, MouseListener {

    private  final Color BACKGRAUND_PADRAO = new Color(184,184,184);
    private  final Color BACKGRAUND_MARCAR = new Color(8,1,247);
    private  final Color BACKGRAUND_EXPLODIR = new Color(189,66,68);
    private  final Color TEXTO_VERDE = new Color(0,100,0);


    private Campo campo;
    public BotaoCampo(Campo campo){
        this.campo = campo;
        setBackground(BACKGRAUND_PADRAO);
        setOpaque(true);
        setBorder(BorderFactory.createBevelBorder(0));

        addMouseListener(this);
        campo.registrarObservador(this);


    }
    public void eventoOcorreu(Campo campo, CampoEvento evento){
        switch (evento){
            case ABRIR:
                aplicarEstiloAbrir();
                break;
            case MARCAR:
                aplicarEstiloMarcar();
                break;
            case EXPLODIR:
                aplicarEstiloExplodir();
                break;
            default:
                aplicarEstiloPadrao();
        }
        SwingUtilities.invokeLater(() ->{
            repaint();
            validate();
        });
    }
    private void aplicarEstiloAbrir(){
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        if (campo.isMinado()){
            setBackground(BACKGRAUND_EXPLODIR);
            return;
        }
        setBackground(BACKGRAUND_PADRAO);

        switch (campo.minasNaVizinhanca()){
            case 1:
                setForeground(TEXTO_VERDE);
                break;
            case 2:
                setForeground(Color.BLUE);
                break;
            case 3:
            setForeground(Color.YELLOW);
            break;
            case 4:
            case 5:
            case 6:
                setForeground(Color.RED);
                break;
            default:
                setForeground(Color.PINK);
        }
        String valor = !campo.visinhancaSegura() ? campo.minasNaVizinhanca() + "" : "";
        setText(valor);
    }
    private void aplicarEstiloPadrao(){
        setBackground(BACKGRAUND_PADRAO);
        setBorder(BorderFactory.createBevelBorder(0));
        setText("");

    }
    private void aplicarEstiloExplodir(){
        setBackground(BACKGRAUND_EXPLODIR);
        setForeground(Color.WHITE);
        setText("X");

    }
    private void aplicarEstiloMarcar(){
        setBackground(BACKGRAUND_MARCAR);
        setForeground(Color.BLACK);
        setText("M");
    }

    //Interface dos eventos do mause
    @Override
    public void mousePressed(MouseEvent e){
        if(e.getButton() == 1 ){
            campo.abrir();
        }else {
            campo.alterarMarcacao();
        }

    }
    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}

}
