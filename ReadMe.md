# FindWord

This is a simple finding word game written in Java. The words are loaded by an url but you may change it by yourself if you wish.

# Logic

After loading words, a random word is selected from Json array and a end-dashed version of it is created. The user has number of attempts that same as length of word. In every attempt, user will guess a letter that selected word contains. If selected word contains predicted letter, end-dashed will be replaced by letter.