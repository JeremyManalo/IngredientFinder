#------------------------------------------------------------------------------
# Another Makefile with macros
#------------------------------------------------------------------------------

JAVASRC    = IngredientFinder.java
SOURCES    = README Makefile $(JAVASRC)
MAINCLASS  = IngredientFinder
CLASSES    = IngredientFinder.class
JARFILE    = IF.jar

all: $(JARFILE)

$(JARFILE): $(CLASSES)
	echo Main-class: $(MAINCLASS) > Manifest
	jar cvfm $(JARFILE) Manifest $(CLASSES)
	rm Manifest
	chmod +x $(JARFILE)

$(CLASSES): $(JAVASRC)
	javac -Xlint $(JAVASRC)

clean:
	rm $(CLASSES) $(JARFILE)
