package model;

import java.io.Serializable;

public class CardUpdateMessage implements Serializable {
 private static final long serialVersionUID = 1L;

 private String message;
 private String cardId;

 public CardUpdateMessage() {
 }

 public CardUpdateMessage(String message, String cardId) {
     this.message = message;
     this.cardId = cardId;
 }

 // Getters and setters
 public String getMessage() {
     return message;
 }

 public void setMessage(String message) {
     this.message = message;
 }

 public String getCardId() {
     return cardId;
 }

 public void setCardId(String cardId) {
     this.cardId = cardId;
 }
}
