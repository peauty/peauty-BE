package com.peauty.domain.puppy;

import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Breed {

    // 소형견
    AFFENPINSCHER("Affenpinscher", PuppySize.SMALL),
    AUSTRALIAN_TERRIER("Australian Terrier", PuppySize.SMALL),
    BASENJI("Basenji", PuppySize.SMALL),
    BEAGLE("Beagle", PuppySize.SMALL),
    BICHON_FRISE("Bichon Frise", PuppySize.SMALL),
    BORDER_TERRIER("Border Terrier", PuppySize.SMALL),
    BOSTON_TERRIER("Boston Terrier", PuppySize.SMALL),
    BRUSSELS_GRIFFON("Brussels Griffon", PuppySize.SMALL),
    CAIRN_TERRIER("Cairn Terrier", PuppySize.SMALL),
    CHIHUAHUA("Chihuahua", PuppySize.SMALL),
    CHINESE_CRESTED("Chinese Crested", PuppySize.SMALL),
    DACHSHUND("Dachshund", PuppySize.SMALL),
    ENGLISH_TOY_SPANIEL("English Toy Spaniel", PuppySize.SMALL),
    FRENCH_BULLDOG("French Bulldog", PuppySize.SMALL),
    JACK_RUSSELL_TERRIER("Jack Russell Terrier", PuppySize.SMALL),
    JAPANESE_SPANIEL("Japanese Spaniel", PuppySize.SMALL),
    LAKELAND_TERRIER("Lakeland Terrier", PuppySize.SMALL),
    LHASA_APSO("Lhasa Apso", PuppySize.SMALL),
    MALTESE("Maltese", PuppySize.SMALL),
    MANCHESTER_TERRIER("Manchester Terrier", PuppySize.SMALL),
    NORWICH_TERRIER("Norwich Terrier", PuppySize.SMALL),
    PAPILLON("Papillon", PuppySize.SMALL),
    PEKINGESE("Pekingese", PuppySize.SMALL),
    POMERANIAN("Pomeranian", PuppySize.SMALL),
    PUG("Pug", PuppySize.SMALL),
    SCHIPPERKE("Schipperke", PuppySize.SMALL),
    SCOTTISH_TERRIER("Scottish Terrier", PuppySize.SMALL),
    SEALYHAM_TERRIER("Sealyham Terrier", PuppySize.SMALL),
    SHETLAND_SHEEPDOG("Shetland Sheepdog", PuppySize.SMALL),
    SHIHTZU("ShihTzu", PuppySize.SMALL),
    SILKY_TERRIER("Silky Terrier", PuppySize.SMALL),
    SKYE_TERRIER("Skye Terrier", PuppySize.SMALL),
    TIBETAN_TERRIER("Tibetan Terrier", PuppySize.SMALL),
    WELSH_TERRIER("Welsh Terrier", PuppySize.SMALL),
    WEST_HIGHLAND_WHITE_TERRIER("West Highland White Terrier", PuppySize.SMALL),
    YORKSHIRE_TERRIER("Yorkshire Terrier", PuppySize.SMALL),

    // 중형견
    AIREDALE_TERRIER("Airedale Terrier", PuppySize.MEDIUM),
    AMERICAN_STAFFORDSHIRE_TERRIER("American Staffordshire Terrier", PuppySize.MEDIUM),
    AMERICAN_WATER_SPANIEL("American Water Spaniel", PuppySize.MEDIUM),
    AUSTRALIAN_CATTLE_DOG("Australian Cattle Dog", PuppySize.MEDIUM),
    AUSTRALIAN_SHEPHERD("Australian Shepherd", PuppySize.MEDIUM),
    BASSET_HOUND("Basset Hound", PuppySize.MEDIUM),
    BEARDED_COLLIE("Bearded Collie", PuppySize.MEDIUM),
    BLACK_AND_TAN_COONHOUND("Black And Tan Coonhound", PuppySize.MEDIUM),
    BORDER_COLLIE("Border Collie", PuppySize.MEDIUM),
    BULL_TERRIER("Bull Terrier", PuppySize.MEDIUM),
    BULLDOG("Bulldog", PuppySize.MEDIUM),
    CANAAN_DOG("Canaan Dog", PuppySize.MEDIUM),
    CHINESE_SHAR_PEI("Chinese Shar Pei", PuppySize.MEDIUM),
    CLUMBER_SPANIEL("Clumber Spaniel", PuppySize.MEDIUM),
    COCKER_SPANIEL("Cocker Spaniel", PuppySize.MEDIUM),
    DALMATIAN("Dalmatian", PuppySize.MEDIUM),
    ENGLISH_COCKER_SPANIEL("English Cocker Spaniel", PuppySize.MEDIUM),
    ENGLISH_SETTER("English Setter", PuppySize.MEDIUM),
    ENGLISH_SPRINGER_SPANIEL("English Springer Spaniel", PuppySize.MEDIUM),
    FINNISH_SPITZ("Finnish Spitz", PuppySize.MEDIUM),
    FOX_HOUND("Fox Hound", PuppySize.MEDIUM),
    GERMAN_SHORTHAIRED_POINTER("German Shorthaired Pointer", PuppySize.MEDIUM),
    GERMAN_WIREHAIRED_POINTER("German Wirehaired Pointer", PuppySize.MEDIUM),
    GORDON_SETTER("Gordon Setter", PuppySize.MEDIUM),
    IRISH_WATER_SPANIEL("Irish Water Spaniel", PuppySize.MEDIUM),
    KEESHOND("Keeshond", PuppySize.MEDIUM),
    KERRY_BLUE_TERRIER("Kerry Blue Terrier", PuppySize.MEDIUM),
    POODLE("Poodle", PuppySize.MEDIUM),
    RHODESIAN_RIDGEBACK("Rhodesian Ridgeback", PuppySize.MEDIUM),
    STAFFORDSHIRE_BULLTERRIER("Staffordshire Bull Terrier", PuppySize.MEDIUM),
    SOFT_COATED_WHEATEN_TERRIER("Soft Coated Wheaten Terrier", PuppySize.MEDIUM),
    SUSSEX_SPANIEL("Sussex Spaniel", PuppySize.MEDIUM),
    VIZSLA("Vizsla", PuppySize.MEDIUM),

    // 대형견
    AFGHAN_HOUND("Afghan Hound", PuppySize.LARGE),
    AKITA("Akita", PuppySize.LARGE),
    ALASKAN_MALAMUTE("Alaskan Malamute", PuppySize.LARGE),
    BERNESE_MOUNTAIN_DOG("Bernese Mountain Dog", PuppySize.LARGE),
    BLOOD_HOUND("Blood Hound", PuppySize.LARGE),
    BOUVIER_DES_FLANDRES("Bouvier Des Flandres", PuppySize.LARGE),
    BRIARD("Briard", PuppySize.LARGE),
    BULLMASTIFF("Bullmastiff", PuppySize.LARGE),
    CHESAPEAKE_BAY_RETRIEVER("Chesapeake Bay Retriever", PuppySize.LARGE),
    CURLY_COATED_RETRIEVER("Curly Coated Retriever", PuppySize.LARGE),
    DOBERMAN_PINSCHER("Doberman Pinscher", PuppySize.LARGE),
    FLAT_COATED_RETRIEVER("Flat Coated Retriever", PuppySize.LARGE),
    GOLDEN_RETRIEVER("Golden Retriever", PuppySize.LARGE),
    GREAT_DANE("Great Dane", PuppySize.LARGE),
    GREYHOUND("Greyhound", PuppySize.LARGE),
    IRISH_SETTER("Irish Setter", PuppySize.LARGE),
    IRISH_WOLFHOUND("Irish Wolfhound", PuppySize.LARGE),
    KOMONDOR("Komondor", PuppySize.LARGE),
    KUVASZ("Kuvasz", PuppySize.LARGE),
    LABRADOR_RETRIEVER("Labrador Retriever", PuppySize.LARGE),
    MASTIFF("Mastiff", PuppySize.LARGE),
    NEWFOUNDLAND("Newfoundland", PuppySize.LARGE),
    OTTERHOUND("Otterhound", PuppySize.LARGE),
    ROTTWEILER("Rottweiler", PuppySize.LARGE),
    SAINT_BERNARD("Saint Bernard", PuppySize.LARGE),
    SAMOYED("Samoyed", PuppySize.LARGE),
    SCOTTISH_DEERHOUND("Scottish Deerhound", PuppySize.LARGE),
    WEIMARANER("Weimaraner", PuppySize.LARGE);

    private final String breedName;
    private final PuppySize puppySize;

    Breed(String breedName, PuppySize puppySize) {
        this.breedName = breedName;
        this.puppySize = puppySize;
    }

    public static Breed from(String breedName) {
        return Arrays.stream(Breed.values())
                .filter(it -> it.breedName.equalsIgnoreCase(breedName))
                .findFirst()
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXISTS_BREED));
    }
}


/*
    Affenpinscher,
    Afghan_Hound,
    Airedale_Terrier,
    Akita,
    Alaskan_Malamute,
    American_Staffordshire_Terrier,
    American_Water_Spaniel,
    Australian_Cattle_Dog,
    Australian_Shepherd,
    Australian_Terrier,
    Basenji,
    Basset_Hound,
    Beagle,
    Bearded_Collie,
    Bedlington_Terrier,
    Bernese_Mountain_Dog,
    Bichon_Frise,
    Black_And_Tan_Coonhound,
    Blood_Hound,
    Border_Collie,
    Border_Terrier,
    Borzoi,
    Boston_Terrier,
    Bouvier_Des_Flandres,
    Boxer,
    Briard,
    Brittany,
    Brussels_Griffon,
    BullTerrier,
    Bulldog,
    Bullmastiff,
    Cairn_Terrier,
    Canaan_Dog,
    Chesapeake_Bay_Retriever,
    Chihuahua,
    Chinese_Crested,
    Chinese_Shar_Pei,
    Chow_Chow,
    Clumber_Spaniel,
    Cocker_Spaniel,
    Collie,
    Curly_Coated_Retriever,
    Dachshund,
    Dalmatian,
    Doberman_Pinscher,
    English_Cocker_Spaniel,
    English_Setter,
    English_Springer_Spaniel,
    English_Toy_Spaniel,
    Eskimo_Dog,
    Finnish_Spitz,
    Flat_Coated_Retriever,
    Fox_Terrier,
    Fox_hound,
    French_Bulldog,
    German_Shepherd,
    German_Shorthaired_Pointer,
    German_Wirehaired_Pointer,
    Golden_Retriever,
    Gordon_Setter,
    Great_Dane,
    Greyhound,
    Irish_Setter,
    Irish_Water_Spaniel,
    Irish_Wolfhound,
    Jack_Russell_Terrier,
    Japanese_Spaniel,
    Keeshond,
    Kerry_Blue_Terrier,
    Komondor,
    Kuvasz,
    Labrador_Retriever,
    Lakeland_Terrier,
    Lhasa_Apso,
    Maltese,
    Manchester_Terrier,
    Mastiff,
    Mexican_Hairless,
    Newfoundland,
    Norwegian_Elkhound,
    Norwich_Terrier,
    Otterhound,
    Papillon,
    Pekingese,
    Pointer,
    Pomeranian,
    Poodle,
    Pug,
    Puli,
    Rhodesian_Ridgeback,
    Rottweiler,
    Saint_Bernard,
    Saluki,
    Samoyed,
    Schipperke,
    Schnauzer,
    Scottish_Deerhound,
    Scottish_Terrier,
    Sealyham_Terrier,
    Shetland_Sheepdog,
    ShihTzu,
    Siberian_Husky,
    Silky_Terrier,
    Skye_Terrier,
    Staffordshire_BullTerrier,
    Soft_Coated_Wheaten_Terrier,
    Sussex_Spaniel,
    Spitz,
    Tibetan_Terrier,
    Vizsla,
    Weimaraner,
    Welsh_Terrier,
    West_Highland_White_Terrier,
    Whippet,
    Yorkshire_Terrier
    */
