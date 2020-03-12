/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazegamereal;

/**
 *
 * @author cuddy_864631
 */
public class Vote {
    private String castedVote;
    
    public Vote()
    {
        castedVote = null;
    }

    public Vote(String castedVote) {
        this.castedVote = castedVote.toUpperCase();
    }

    public String getCastedVote() {
        return castedVote;
    }

    public void setCastedVote(String castedVote) {
        this.castedVote = castedVote.toUpperCase();
    }
    
    @Override
    public String toString()
    {
        return castedVote;
    }
    
}
