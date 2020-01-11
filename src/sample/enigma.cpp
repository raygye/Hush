#include <iostream>
#include <bits/stdc++.h>
#include <cmath>

char *scramble( char *original, unsigned long key ) {
    unsigned char s[256];
    for (int a = 0; a < 256; a++) {
        s[a] = a;
    }
    int div4 = (int) (ceil(strlen(original) / 4.0) * 4);
    char *newsize = new char[div4];
    //fills new array with null
    memset(newsize, 0, sizeof(char) * div4);
    strcpy(newsize, original);
    //scrambling/algorithm 1
    unsigned int i = 0;
    unsigned int j = 0;
    unsigned int k =0;
    for (int a = 0; a < 256; a++) {
        k = i % 64;
        j = (j + s[i] + ((key >> k) & 1ULL)) % 256;
        unsigned long temp =  s[i];
        s[i] = s[j];
        s[j] = temp;
        i = (i + 1) % 256;
    }
    //algorithm 2
    for (int c = 0; c < div4; c++){
        i = (i + 1) % 256;
        j = (j + s[i]) % 256;
        unsigned char temp = s[j];
        s[j] = s[i];
        s[i] = temp;
        unsigned char r = s[(s[i] + s[j]) % 256];
        newsize[c] = r^newsize[c];
    }
    //ASCII Armour
    //new array is 1.2 times the size
    int newlen = (div4 / 4) * 5;
    int newsizeindex = 4;
    //final encrypted char
    char *encrypt = new char[newlen + 1];
    //iterate through addresses (every four)
    int plainlen = strlen(original);
    for (char *a = newsize; a <= (newsize + plainlen); a += 4) {
        unsigned int bigdec = 0;
        for (int b = 0; b < 4; b++) {
            unsigned char curchar = *(a + b);
            //concatenating to make large decimal number, bit shifting (3 - b) bytes to the left, 3 - b to reverse order
            bigdec |= (curchar << ((3 - b) * 8));
        }
        for (int i = 0; i < 5; i++) {
            //decimal to base 85
            encrypt[newsizeindex - i] = (bigdec % 85) + 33;
            bigdec /= 85;
        }
        newsizeindex += 5;
    }
    encrypt[newlen] = 0;
    return encrypt;
}
char *unscramble(char *encrypted, unsigned long key) {
    int clen = strlen(encrypted);
    //original len is 0.8 the size
    int newlen = (clen / 5) * 4;
    char *original = new char[newlen + 1];
    original[0] = 0;
    int textindex = 0;
    //iterates through addresses
    for (char *a = encrypted; a < (encrypted + clen); a += 5) {
        //total base 10 value of sum of ascii
        unsigned int bigdec = 0;
        for (int i = 0; i < 5; i++) {
            //total base 10 += 85 to the power of changed ascii value
            bigdec += ((int) pow(85, (4 - i))) * (*(a + i)-33);
        }
        //original text
        unsigned char org[4] = {0, 0 ,0 ,0};
        for (int i = 0; i < 4; i++) {
            //get char from base 10 total by bitshifting (3-i) bytes to the right, 3 - i to reverse order
            char asc = bigdec >> ((3 - i) * 8) & 0b11111111;
            org[i] = asc;
        }
        //copies the original asciis into the 0.8 sized array
        for (int i = 0; i < 4; i++){
            original[textindex] = org[i];
            textindex++;
        }
    }
    unsigned char s[256];
    for (int i = 0; i < 256; i++) {
        s[i] = i;
    }
    //algorithm 1
    unsigned int i = 0;
    unsigned int j = 0;
    for (int a = 0; a < 256; a++) {
        unsigned int k = i % 64;
        j = (j + s[i] + ((key >> k) & 1ULL)) % 256;
        unsigned int temp = s[i];
        s[i] = s[j];
        s[j] = temp;
        i = (i + 1) % 256;
    }
    //algorithm 2
    char *decrypt = new char[newlen + 1];
    for (int a = 0; a < newlen; a++) {
        i = (i + 1) % 256;
        j = (j + s[i]) % 256;
        unsigned char temp = s[i];
        s[i] = s[j];
        s[j] = temp;
        int r = ((int) s[i] + (int) s[j]) % 256;
        decrypt[a] = original[a] ^ s[r];
    }
    decrypt[newlen] = 0;
    return decrypt;
}
int main() {
    int sel;
    std::string trans, a;
    unsigned long key;
    char* text;
    std::cin >> sel;
    std::cin >> key;
    std::getline(std::cin,a);
    trans=a;
    //you gotta do it twice man you gotta do it TWICE
    std::getline(std::cin,a);
    trans=a;
    while (true){
        std::getline(std::cin,a);
        if (a == "sysclose29110391039484horse") {
            break;
        }
        else if (a == trans) {
            break;
        }
        trans+=("\n" + a);
    }
    char *translate = new char[trans.length()+1];
    strcpy(translate, trans.c_str());
    if (sel == 0) {
        text = scramble(translate, key);
        std::cout << text;
    }
    else {
        text = unscramble(translate, key);
        std::cout << text;
    }
    delete [] translate;
    return 0;
}
