package com.skillw.mono.game;

import com.skillw.mono.card.Money;

/**
 * Where to store and withdraw money
 */
public class Bank {
    private Money[] money;
    private int capacity;
    private int size;

    public Bank() {
        this.capacity = 10;
        this.money = new Money[this.capacity];
    }

    private void resize(){
        this.capacity *= 2;
        Money[] newArray = new Money[this.capacity];
        for(int i = 0; i < money.length; i++){
            newArray[i] = this.money[i];
        }
        this.money = newArray;
    }


    public void add(Money money) {
        if(this.size == this.capacity) resize();
        this.money[this.size++] = money;
    }

}
