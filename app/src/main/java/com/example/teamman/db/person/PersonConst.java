package com.example.teamman.db.person;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PersonConst {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String lastName;
    public String firstName;
    public String patronymic;
    public Boolean sex;
    public String birthDate;
    public String citizenship;
    public String placeOfBirth;
    public String bloodType;

    public PersonConst() {} // пустой конструктор нужен Room

    public static class Builder {
        private String lastName;
        private String firstName;
        private String patronymic;
        private Boolean sex = true;
        private String birthDate = "2000-01-01";
        private String citizenship = "Ukraine"; //TODO
        private String placeOfBirth = "Ukraine"; //TODO
        private String bloodType = "O(1)";

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder patronymic(String patronymic) {
            this.patronymic = patronymic;
            return this;
        }

        public Builder sex(Boolean sex) {
            this.sex = sex;
            return this;
        }

        public Builder birthDate(String birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder citizenship(String citizenship) {
            this.citizenship = citizenship;
            return this;
        }

        public Builder placeOfBirth(String placeOfBirth) {
            this.placeOfBirth = placeOfBirth;
            return this;
        }
        public Builder bloodType(String bloodType) {
            this.bloodType = bloodType;
            return this;
        }

        public PersonConst build() {
            PersonConst e = new PersonConst();
            e.lastName = this.lastName;
            e.firstName = this.firstName;
            e.patronymic = this.patronymic;
            e.sex = this.sex;
            e.birthDate = this.birthDate;
            e.citizenship = this.citizenship;
            e.placeOfBirth = this.placeOfBirth;
            e.bloodType = this.bloodType;
            return e;
        }
    }


}