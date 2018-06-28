(ns loans-statuses.core
  (:require [clj-http.client :as client])
  (:gen-class))

(def loans '[43143519 43146365 43146383 43148983 43150218 43150735 43151513 43152284 43152327 43153644 43156313 43157762 43157906 43160923 43160934 43162075 43165929 43166252 43171288 43177054 43177725 43178036 43180047 43183289 43183505 43183545 43184154 43184212 43184742 43184913 43185049 43185122 43185217 43185393 43185744 43186006 43186213 43187285 43187762 43188140 43188251 43188492 43188656 43188901 43188910 43189773 43190087 43190269 43190559 43191001 43191133 43191465 43191889 43192416 43192454 43192773 43192914 43192941 43193236 43193394 43194011 43194153 43194427 43195555 43196404 43198301 43198440 43198677 43198948 43199299 43199395 43199786 43199988 43200152 43200316 43200470 43200609 43201031 43201327 43201335 43201339 43201547 43201582 43201734 43202078 43202291 43204291 43204555 43206100 43207095 43207819 43209009 43209689 43209968 43210802 43211427 43211610 43212345 43213939 43214959 43215871 43219634 43221440 43221638 43223514 43224059 43224470 43224966 43227912 43229962 43230039 43230043 43230214 43230739 43231373 43232613 43234801 43237372 43239822 43240169 43240355 43240853 43241200 43243510 43243973 43245593 43245807 43247126 43248115 43249858 43249889 43250821 43251641 43252208 43252218 43252236 43252390 43252409 43253041 43253169 43253177 43253398 43253837 43253849 43253880 43254114 43255261 43255753 43255934 43256350 43256757 43257076 43257489 43257499 43257515 43257670 43258022 43258052 43258217 43258219 43258290 43258383 43258563 43258588 43258745 43259104 43259120 43259287 43259289 43259511 43260051 43260489 43260681 43261394 43261463 43262640 43262760 43263289 43263360 43263777 43263781 43263811 43263962 43264894 43264936 43265365 43265624 43265848 43266323 43266995 43267266 43267791 43268154 43268230 43268332 43268488 43268728 43269927 43270764 43271092 43271223 43271594 43271735 43272290 43273155 43273163 43273279 43273885 43273918 43274211 43274233 43274363 43274371 43274506 43274671 43274800 43274801 43274922 43274923 43275291 43275757 43275773 43276740 43277171 43277448 43277566 43278096 43278518 43279020 43279022 43279153 43279444 43279677 43280026 43280257 43280260 43281024 43281566 43281957 43282392 43285340 43290910 43291792 43294396 43295587 43297157 43298041 43298250 43299471 43301109 43303845 43305956 43306025 43307097 43307718 43308417 43308455 43308497 43308788 43310247 43311591 43311733 43311874 43312349 43312485 43312604 43313033 43313036 43313358 43314483 43314565 43314835 43315017 43315114 43315158 43316906 43317793 43317874 43318511 43318586 43318953 43319296 43319490 43319919 43320125 43322014 43324129 43324149 43324482 43324536 43324737 43324743 43325735 43326197 43326302 43326327 43326532 43326622 43326738 43327215 43327408 43327569 43327582 43328239 43328329 43328501 43328576 43328636 43329146 43333046 43336576 43336742 43337737 43338031 43338068 43338815 43338838 43339146 43339178 43339263 43339451 43339583 43339704 43339743 43339749 43339950 43340169 43340938 43341366 43341474 43341703 43341774 43342172 43342206 43342350 43342530 43342671 43342674 43343136 43343342 43343404 43343529 43343553 43343791 43344348 43345249 43345670 43345671 43346173 43346387 43347365 43347418 43347482 43347765 43348454 43348482 43348729 43348979 43349202 43349337 43349559 43349707 43349796 43350123 43350176 43350367 43350422 43350441 43350463 43350697 43351363 43351429 43351589 43351915 43351942 43352027 43352089 43352617 43354009 43354059 43354306 43355361 43355761 43356804 43357727 43358363 43358429 43358461 43358603 43358643 43359023 43359187 43359201 43359204 43359566 43359618 43359753 43360352 43360721 43360723 43361031 43361071 43361225 43361244 43361515 43361533 43361578 43361725 43362024 43362107 43362146 43362195 43362486 43362759 43363022 43363069 43363134 43363208 43363381 43363592 43363594 43363609 43363804 43364037 43364259 43365168 43365175 43365725 43365915 43365939 43366021 43366329 43366565 43367102 43367871 43368032 43368035 43368145 43368158 43368289 43368791 43369035 43369068 43369276 43369666 43370141 43371982 43372779 43377013 43380083 43380445 43380736 43380966 43382355 43387304 43387310 43388573 43388714 43389482 43390672 43390868 43391576 43391990 43393379 43393632 43393675 43394060 43394336 43394625 43396189 43397335 43397675 43398104 43400573 43403246 43403507 43403614 43403785 43404045 43404103 43404319 43405060 43405172 43405278 43405707 43405722 43407125 43408107 43408188 43408195 43408206 43408432 43408635 43409188 43409249 43409262 43409550 43409563 43409576 43410086 43410309 43410486 43410558 43411122 43411759 43412671 43412674 43412677 43413110 43413164 43413176 43413508 43413629 43413643 43413915 43413930 43414137 43414256 43414301 43414365 43414635 43415329 43415490 43415546 43415553 43415562 43415584 43415920 43416282 43416587 43416640 43416956 43416964 43417263 43418089 43418364 43418374 43418691 43418832 43419067 43419174 43419206 43419454 43419829 43419916 43420008 43420211 43420334 43420683 43420721 43421047 43421255 43421263 43421356 43421506 43421684 43421738 43422500 43423044 43423080 43423125 43423282 43423338 43423612 43423828 43424439 43424686 43424702 43425014 43425443 43425466 43425581 43425682 43425693 43425889 43426657 43427097 43427393 43427842 43428222 43428666 43429024 43429049 43429291 43429737 43430026 43430484 43430796 43431171 43431390 43431445 43431740 43431869 43431874 43432022 43432272 43432454 43432601 43432617 43432808 43433189 43433212 43433346 43433369 43433380 43433561 43433591 43433734 43434129 43434451 43434614 43435289 43435727 43435968 43436016 43436095 43436137 43436138 43436555 43436596 43436663 43436762 43437164 43437187 43437700 43437778 43438361 43438430 43438494 43438706 43439347 43439366 43439430 43439600 43439628 43440260 43441039 43441091 43441153 43442139 43442328 43442359 43442463 43442715 43443032 43443902 43444699 43445263 43445346 43445353 43445487 43445503 43445733 43445810 43445838 43446075 43446158 43446528 43446626 43446779 43446874 43446883 43446991 43447372 43448029 43448545 43449057 43452134 43453501 43454016 43455138 43455503 43455629 43457252 43457581 43459621 43459648 43460523 43460779 43462063 43462606 43463440 43464880 43464884 43466419 43468458 43469677 43471130 43471786 43471904 43472036 43472577 43474197 43474619 43474810 43475815 43476936 43478112 43478117 43478447 43478457 43479444 43479462 43479676 43480067 43480648 43480678 43480759 43481364 43482139 43482277 43482779 43483132 43483342 43483624 43484181 43484188 43484232 43484494 43484667 43484704 43484943 43485371 43485607 43485693 43486176 43486840 43487192 43487243 43487865 43488247 43488438 43488966 43489698 43489899 43490138 43490220 43490929 43490935 43491216 43491432 43491478 43491733 43492244 43492350 43493133 43494740 43495035 43495239 43495288 43495787 43496364 43496554 43497286 43497329 43498412 43498890 43499432 43499702 43500225 43500257 43500463 43501342 43502390 43502440 43502508 43502730 43502778 43505117 43505760 43506204 43506220 43506378 43506588 43506769 43507441 43507721 43508619 43508868 43508877 43508930 43509110 43509826 43510016 43510106 43510111 43510432 43510737 43511607 43512772 43513099 43513596 43513901 43514835 43514954 43515326 43515528 43515957 43517084 43517309 43519382 43520380 43520616 43520633 43520840 43521177 43521447 43521462 43521828 43522578 43522802 43524368 43524855 43524860 43525036 43525192 43525548 43526083 43526786 43527230 43528150 43532016 43532270 43535398 43535965 43543089 43544746 43547411 43548399 43548623 43549091 43550537 43551657 43552678 43552785 43554285 43554934 43555994 43559112 43559311 43560844 43560950 43560976 43561143 43561959 43562176 43562234 43562582 43562690 43562948 43563006 43563041 43563228 43563269 43563409 43563935 43564544 43565373 43565547 43565709 43567329 43567924 43568100 43569483 43570733 43570822 43571179 43571464 43572389 43572735 43573374 43573486 43573516 43573733 43574000 43574022 43574077 43574104 43574155 43574161 43574395 43575247 43575294 43575520 43575551 43575653 43575806 43576290 43577071 43577081 43577156 43578169 43579602 43579661 43580903 43581346 43581559 43581800 43582172 43582561 43583189 43583234 43585220 43585563 43586022 43586325 43587216 43588181 43588226 43588600 43588837 43589105 43589662 43590179 43590418 43590768 43590826 43590827 43592195 43592908 43593542 43593905 43594545 43594640 43596858 43597211 43597551 43597552 43597793 43598085 43598210 43598514 43598883 43599362 43602072 43602119 43602194 43602392 43602984 43603551 43605833 43606748 43607239 43608139 43608383 43609007 43609014 43609206 43609567 43610288 43610591 43611113 43611654 43612053 43612148 43612271 43612409 43612425 43612628 43621247 43622214 43625476 43627366 43631549 43633795 43634105 43634660 43635460 43635483 43638904 43640841 43640972 43642558 43643615 43643757 43643766 43644019 43644842 43645023 43645247 43645258 43647190 43648207 43648237 43648268 43648754 43648915 43649195 43649572 43649971 43650462 43650559 43650881 43651244 43651279 43652188 43654802 43655755 43655950 43656448 43656643 43656855 43657668 43657712 43658003 43658019 43658218 43658492 43658517 43658857 43658919 43658924 43660031 43660343 43660418 43661050 43661861 43661863 43662635 43662930 43663042 43663139 43663250 43663480 43663908 43663972 43664353 43664412 43665324 43665687 43665956 43666042 43666440 43666739 43666866 43666874 43667071 43667190 43667270 43667311 43667810 43667811 43667995 43668033 43668044 43668050 43668067 43668187 43668262 43668790 43668793 43669548 43669948 43669959 43670170 43670372 43670417 43670590 43670623 43670879 43671114 43671340 43671660 43671872 43671985 43672607 43672869 43672884 43672997 43673155 43673331 43673404 43673547 43673749 43673849 43674573 43674942 43675432 43676007 43676082 43677227 43677750 43677896 43678768 43678985 43679670 43679788 43679855 43680082 43680914 43681171 43681307 43681506 43682264 43682293 43682322 43682335 43682492 43682676 43683202 43683502 43683958 43684249 43684535 43684976 43685434 43686057 43686685 43687193 43687202 43687205 43687423 43687446 43687451 43687843 43688045 43688243 43688384 43688469 43688523 43688901 43689411 43689596 43689664 43689729 43689784 43689793 43690094 43690127 43690216 43690427 43690462 43690483 43690627 43690628 43690790 43691121 43691288 43691323 43691432 43691752 43691758 43691960 43691976 43691990 43692011 43692298 43692568 43692596 43692803 43693005 43693468 43693748 43693925 43693927 43693993 43694131 43694132 43694336 43694486 43694656 43694672 43695121 43695139 43695942 43695953 43695958 43696444 43696504 43696679 43696808 43697063 43697075 43697184 43697890 43697996 43698205 43698224 43698296 43698632 43698774 43698786 43698789 43698863 43699791 43700244 43700757 43704876 43709084 43709684 43716238 43717418 43720710 43721890 43722184 43726980 43727150 43728249 43730191 43731666 43733265 43734146 43735372 43735410 43735592 43735663 43736334 43736496 43737064 43737108 43737297 43738644 43739972 43740027 43740259 43740384 43740958 43741209 43741462 43741480 43741488 43742277 43742631 43742930 43743437 43743660 43743951 43744587 43744602 43744616 43744839 43745000 43745060 43745066 43745103 43745304 43745584 43746016 43746210 43746249 43746265 43746574 43747021 43747181 43747233 43747493 43747563 43747666 43747835 43747893 43748182 43748682 43749540 43749732 43749747 43749899 43749965 43750323 43750341 43750544 43750564 43750574 43750575 43750795 43750822 43750981 43751190 43751208 43751860 43752040 43752247 43752777 43753014 43753593 43753899 43753945 43754094 43754561 43754794 43754801 43755123 43755374 43755576 43755579 43755592 43756304 43756354 43756417 43756504 43756603 43756826 43757551 43757803 43757919 43757922 43757950 43757991 43758130 43758358 43758772 43758915 43759414 43759554 43759744 43759751 43759914 43760130 43760329 43760335 43760630 43760643 43760748 43761234 43761280 43761916 43762456 43762595 43762692 43762889 43763101 43763705 43763707 43763911 43764366 43765356 43765386 43765685 43765709 43765835 43765857 43765946 43765960 43766688 43766966 43767158 43767513 43767910 43768081 43769146 43769362 43769479 43769506 43769528 43769962 43769977 43769990 43770154 43770437 43770446 43770457 43770462 43770807 43770911 43773524 43774770 43776211 43776692 43780033 43783307 43788738 43791207 43791730 43792506 43795084 43795947 43796073 43796128 43796314 43797806 43798728 43798865 43798987 43799119 43799247 43799383 43799810 43799818 43799829 43801978 43803900 43803930 43804674 43804810 43805252 43805617 43806136 43806212 43806702 43806754 43806930 43806931 43807172 43807296 43807356 43807636 43808497 43808717 43808732 43808800 43808955 43808986 43809093 43809350 43809871 43809985 43810118 43810505 43810597 43810877 43811139 43811464 43811736 43811903 43812436 43812606 43813361 43813624 43813671 43814363 43814461 43814731 43815758 43816122 43816958 43817857 43818499 43818775 43819054 43819645 43820674 43821139 43821253 43821637 43821839 43821899 43822250 43822340 43822388 43823254 43823478 43823703 43825995 43826534 43828802 43828827 43828866 43829008 43829572 43830637 43831172 43831173 43831259 43831454 43831870 43831982 43832014 43833791 43833949 43834111 43834288 43834335 43835101 43835348 43837332 43837854 43838813 43838850 43838882 43840710 43840902 43841008 43845228 43845998 43846615 43846648 43846764 43847906 43848352 43850971 43851403 43851811 43852122 43852252 43852773 43852914 43853535 43854147 43854207 43854231 43854813 43855726 43855879 43856298 43856707 43856763 43856853 43856878 43857210 43857415 43857569 43857705 43857780 43857804 43857976 43857989 43858351 43858689 43858986 43859057 43859091 43859659 43860184 43860187 43860229 43860577 43860587 43860738 43860804 43860814 43860906 43860954 43860972 43860977 43861609 43861983 43862141 43862370 43862377 43862379 43862522 43862646 43862951 43863093 43863107 43863120 43863380 43863514 43863881 43863928 43864358 43864519 43864524 43864820 43864986 43865271 43866030 43866224 43866366 43866524 43866689 43866712 43866860 43866954 43866961 43867495 43867842 43867936 43868079 43868265 43868313 43868607 43868634 43869122 43869284 43869301 43869310 43869599 43869841 43870075 43870148 43870234 43870474 43870934 43870970 43871190 43871635 43871868 43872471 43872472 43872774 43873904 43873908 43874002 43874176 43874299 43874698 43874911 43875272 43875426 43875504 43875505 43875777 43875968 43876854 43876963 43877099 43878343 43878459 43878589 43878727 43878873 43878924 43878986 43879270 43879551 43879614 43879717 43879910 43880115 43880254 43880369 43880940 43881487 43881692 43881949 43881959 43882050 43882718 43883016 43883146 43883158 43883466 43883770 43884085 43884141 43884143 43884421 43884658 43885027 43885225 43885666 43886787 43886989 43887382 43887682 43888500 43888646 43888653 43889041 43889289 43889295 43889405 43889633 43890042 43890222 43891452 43893071 43895863 43895882 43896677 43898043 43898119 43898255 43899047 43899334 43900209 43902958 43903591 43908176 43910013 43913803 43913898 43914848 43916881 43916963 43920057 43921081 43921483 43923927 43925254 43925466 43925557 43925712 43925814 43926429 43926570 43927002 43927289 43927720 43928147 43929551 43930344 43931263 43932411 43932476 43932500 43933426 43933775 43934516 43935293 43935450 43935470 43935497 43935505 43935812 43935876 43936125 43936345 43936667 43937023 43937060 43937128 43937431 43937559 43937781 43937958 43939104 43941464 43941907 43942268 43942291 43942536 43942619 43942670 43942905 43943133 43943165 43943422 43943636 43943785 43943845 43944127 43944179 43944181 43944536 43944699 43944788 43944797 43944799 43944961 43945671 43945868 43946677 43947056 43947135 43947235 43948320 43948364 43948738 43948742 43948778 43949544 43949609 43949738 43949800 43950411 43950771 43950810 43950839 43951010 43951394 43952145 43952445 43952520 43952602 43952618 43952992 43953640 43953722 43961973 43961996 43962065 43962080 43962229 43962396 43962519 43962528 43962657 43962682 43962806 43962949 43963371 43963375 43963378 43963384 43963459 43963748 43963771 43963820 43963856 43964281 43964398 43964447 43964615 43964700 43965319 43965582 43965691 43966467 43966740 43966768 43966934 43967427 43968012 43968327 43968984 43969923 43970930 43973418 43974756 43975949 43976780 43979378 43984971 43985258 43986289 43986505 43986772 43986919 43988002 43990323 43990942 43991873 43991983 43992142 43992475 43992496 43992753 43992948 43993561 43993581 43993615 43993844 43993882 43994001 43994157 43994281 43994463 43994859 43995170 43995439 43995566 43996299 43996421 43997023 43997214 43998087 43999683 44000177 44001355 44001545 44001587 44001939 44003075 44005189 44005534 44005970 44007503 44008832 44009286 44009904 44010080 44010224 44010426 44010821 44010831 44011494 44012437 44012930 44013051 44013073 44013352 44013599 44013760 44014132 44014381 44015687 44015727 44015884 44015899 44015934 44016151 44016176 44016351 44016638 44017155 44017213 44017574 44018466 44018503 44019027 44019530 44019619 44020451 44021116 44021281 44022188 44022189 44022338 44022546 44022604 44022747 44022816 44023448 44024095 44024884 44024973 44026586 44026614 44026895 44027028 44027265 44027468 44027947 44028548 44029008 44029394 44029409 44030115 44030582 44032583 44033203 44033311 44039738 44043825 44044182 44044741 44050326 44050408 44050497 44052569 44053897 44054098 44054457 44056040 44057256 44057899 44058073 44058668 44060317 44060419 44061711 44062105 44063615 44063719 44063884 44064461 44064927 44065033 44065466 44065647 44065874 44066201 44066431 44066730 44067288 44067300 44067611 44067838 44068034 44068492 44069063 44069262 44069358 44069471 44069761 44069840 44070084 44070524 44070990 44071868 44072639 44073217 44074396 44074550 44074687 44075666 44076690 44076871 44076872 44077603 44078225 44078978 44079632 44081613 44082007 44082671 44084020 44084360 44084557 44084581 44085210 44086048 44086066 44086431 44086492 44086711 44086814 44087079 44087911 44088176 44088207 44088500 44088506 44089982 44090105 44091439 44091592 44092244 44092577 44094636 44094941 44097880 44099001 44099573 44100788 44101228 44102417 44103106 44103369 44103626 44104478 44104910 44105477 44106053 44107433 44108095 44108399 44108625 44109266 44109670 44110001 44110411 44110649 44110952 44111585 44112142 44112809 44113329 44113877 44114599 44115358 44115932 44116497 44117100 44117570 44117946 44118392 44118911 44119396 44119817 44120132 44120565 44120935 44121398 44121873 44122535 44123020 44123437 44123792 44124473 44125444 44126146 44126842 44127492 44128992 44129594 44130141 44130597 44131004 44131571 44132260 44133164 44133714 44134098 44134591 44136082 44136569 44137154 44137583 44138087 44138603 44139798 44140226 44140783 44141232 44141642 44142335 44142702 44143311 44143839 44144554 44144984 44145600 44146076 44146373 44146858 44147276 44147592 44148369 44148748 44149139 44149498 44149770 44150143 44150630 44150898 44151247 44151551 44151845 44152109 44152494 44152755 44152997 44153305 44153497 44153828 44153831 44154147 44154691 44155097 44155594 44156111 44156309 44156552 44156712 44157123 44157477 44157600 44157602 44157754 44157805 44157849 44157852 44157855 44157871 44157903 44157960 44158000 44158060 44158117 44158262 44158315 44158366 44158383 44158394 44158400 44158420 44158440 44158532 44158546 44158576 44158645 44158667 44158770 44158789 44158863 44159041 44159466 44159606 44159641 44159660 44160150 44160286 44160895 44161255 44161299 44162311 44162597 44162635 44163660 44164188 44168122 44168189 44168435 44168715 44169494 44169678 44170120 44170169 44170532 44170662 44170995 44171185 44171321 44171722 44172031 44172079 44172236 44173926 44173959 44173994 44174152 44174237 44175144 44175398 44175407 44175584 44176454 44176578 44176719 44177016 44177054 44177179 44177222 44177474 44177549 44177665 44178154 44178402 44178433 44178575 44178708 44179259 44179268 44179957 44180365 44180753 44180860 44181001 44181019 44181101 44181161 44181744 44181953 44182187 44182913 44183064 44183135 44183601 44183799 44184064 44184072 44184305 44184376 44184496 44184647 44185224 44185263 44185424 44185621 44185999 44186055 44186843 44187119 44187340 44187899 44187992 44188096 44188718 44189016 44189078 44189107 44189351 44189724 44189808 44190219 44190422 44190524 44190538 44191469 44191704 44191760 44191971 44192018 44192280 44192282 44192492 44192564 44192570 44192648 44193201 44193218 44193281 44194143 44194729 44194872 44195330 44195399 44195774 44195892 44196015 44196185 44196222 44196340 44196559 44196864 44197012 44197028 44197075 44197169 44197322 44197324 44197737 44197831 44198044 44198056 44198083 44198264 44198463 44198797 44198828 44198906 44199298 44199844 44199848 44199862 44200032 44200227 44200269 44200855 44201320 44201354 44201432 44201575 44201874 44201950 44202379 44202675 44202706 44203208 44203522 44203764 44204005 44204439 44204500 44205328 44205548 44206171 44206315 44207207 44208731 44208735 44208874 44208906 44208923 44209060 44209107 44209702 44209966 44210165 44210283 44210304 44211074 44211422 44212015 44212449 44212575 44212989 44213330 44213334 44213383 44213542 44213613 44213826 44214382 44214453 44214481 44214789 44214954 44215142 44215147 44215322 44215990 44216206 44216514 44216566 44216749 44216977 44217131 44217236 44217848 44217899 44218932 44218973 44219387 44219399 44219618 44219721 44222791 44225029 44226226 44227770 44228722 44229653 44233768 44236676 44236694 44236909 44237955 44238845 44239120 44239389 44239672 44239722 44239923 44240124 44240146 44240181 44240308 44240664 44241779 44241865 44242312 44242321 44242444 44242501 44242512 44242805 44243469 44243609 44243617 44243637 44243923 44243986 44244184 44244194 44244305 44244556 44245404 44245449 44245675 44245827 44246159 44246174 44246272 44247130 44247665 44247692 44248103 44248109 44248114 44248439 44248804 44249119 44249452 44249519 44249778 44249798 44250488 44251022 44251049 44251294 44251428 44251580 44251629 44251634 44251722 44252258 44252739 44253799 44254127 44254136 44255347 44255967 44256064 44256123 44256152 44256181 44256206 44256213 44257044 44257185 44257218 44257234 44257455 44258234 44258424 44258718 44258758 44258800 44258911 44259660 44260990 44261360 44261407 44261676 44261829 44262239 44263163 44263716 44264461 44264732 44264827 44265447 44265524 44265553 44265582 44265633 44266375 44266387 44266886 44267183 44267857 44268205 44269189 44269392 44269857 44271560 44272547 44272745 44275217 44276789 44277091 44277739 44278604 44278900 44281053 44281058 44282289 44283079 44283712 44283910 44285080 44285368 44285517 44286333 44288517 44289900 44292975 44296057 44298706 44299167 44299887 44302961 44306685 44310031 44310124 44311753 44313273 44313452 44314390 44314474 44315019 44315274 44316333 44316480 44316799 44316802 44317328 44317357 44318492 44319363 44320450 44320787 44321362 44321849 44322164 44322432 44323380 44323496 44323502 44323559 44325458 44325717 44325800 44325852 44326078 44326395 44326463 44327076 44327113 44327548 44327658 44328042 44328338 44328758 44328923 44329307 44329381 44329788 44329920 44330192 44330899 44331984 44332123 44332332 44332790 44332874 44333706 44333986 44334208 44334898 44335009 44335066 44335278 44335379 44336256 44336261 44336405 44336408 44336445 44336880 44337113 44337360 44337740 44338021 44338033 44338143 44338186 44338417 44338509 44338538 44338540 44338751 44338917 44339481 44339589 44339645 44340051 44340346 44340785 44340798 44341011 44341025 44341612 44341765 44341891 44342101 44342241 44342419 44342436 44342487 44342501 44342503 44342509 44342517 44342532 44342600 44342649 44342660 44342712 44342719 44342744 44342751 44342793 44342814 44342863 44343267 44343342 44343412 44343473 44343545 44343585 44343588 44343610 44343902 44344272 44344730 44345507 44345677 44345958 44346041 44346124 44346354 44346745 44346772 44347021 44347336 44347789 44348057 44348086 44348163 44348315 44348335 44348349 44351579 44351583 44351617 44351624 44351633 44351786 44351891 44351892 44351985 44352283 44352409 44352563 44352777 44352866 44353097 44353240 44353485 44353930 44354387 44362148 44363098 44365519 44365703 44366637 44368593 44368716 44368781 44369032 44369405 44370331 44371703 44371710 44372910 44373423 44373985 44374666 44376080 44376464 44376871 44376957 44377449 44377724 44378028 44378781 44378803 44378830 44379089 44379098 44379354 44379398 44379912 44380134 44380298 44380847 44380891 44381043 44381097 44382174 44382195 44382200 44382215 44382254 44382261 44382522 44382771 44383113 44383449 44383684 44383854 44384248 44384456 44384524 44384605 44384831 44385030 44385290 44385321 44385356 44385379 44385611 44385718 44386455 44386513 44386671 44387296 44387465 44389243 44389394 44389845 44390124 44390164 44390175 44390469 44390530 44390578 44390795 44391098 44391709 44392205 44392715 44392921 44393170 44393551 44394491 44394582 44394670 44394977 44395300 44395506 44395524 44395835 44395845 44396636 44396862 44396900 44397448 44397481 44397713 44397845 44398196 44398858 44399235 44399413 44399440 44399646 44399651 44399823 44399971 44400142 44400978 44401059 44401417 44402132 44402515 44402879 44403863 44404252 44404656 44404759 44405391 44405797 44405800 44406126 44406644 44407402 44407958 44408007 44408187 44408568 44408605 44408808 44408968 44410130 44412079 44412099 44412163 44412911 44416018 44419188 44422972 44424505 44425497 44427123 44427148 44427863 44429568 44429600 44429738 44430135 44431689 44432378 44432503 44433125 44434392 44435167 44435178 44437357 44438576 44438934 44439641 44440308 44440996 44443338 44443772 44444241 44444296 44444701 44446171 44446285 44446591 44447835 44448951 44449847 44450175 44450187 44450889 44451376 44451635 44454043 44454379 44455081 44455456 44456889 44458252 44458673 44458831 44459412 44460338 44461179 44461482 44462048 44464131 44464638 44465076 44465135 44466642 44467120 44467419 44467710 44468975 44468980 44469219 44469311 44469431 44469800 44470116 44470745 44473675 44473946 44474321 44476469 44476607 44477807 44481407 44482536 44483694 44483790 44484748 44484803 44484809 44485076 44488923 44489541 44494989 44498020 44498577 44502157 44502399 44502965 44503555 44503757 44504638 44504971 44506481 44508936 44511071 44511075 44511635 44513036 44513726 44514562 44515719 44516145 44517338 44517690 44518696 44518852 44520873 44521102 44521344 44522852 44524038 44525447 44526686 44528289 44528750 44528917 44529590 44532272 44533668 44533912 44533934 44534500 44534724 44535050 44535608 44535892 44536188 44536479 44537219 44538186 44538706 44539472 44539903 44540058 44540459 44540576 44541163 44541670 44542615 44542892 44543647 44543878 44544109 44544506 44544647 44545161 44545804 44546317 44546338 44546536 44546803 44547642 44548023 44549477 44551524 44552180 44552787 44552912 44552986 44553121 44553757 44554879 44555397 44556173 44556233 44557546 44558145 44558372 44558726 44558859 44559065 44560587 44566774 44567001 44567261 44568246 44572146 44572275 44572903 44575022 44575124 44577400 44578174 44580434 44580705 44580773 44580970 44581460 44581679 44582137 44583127 44583502 44583919 44583987 44584258 44586494 44586513 44587370 44588281 44588539 44589068 44589232 44590024 44590576 44590685 44591478 44592884 44593946 44594379 44594472 44595082 44595363 44596503 44596786 44596990 44597033 44597651 44598707 44598836 44599553 44599990 44600115 44600216 44600657 44600918 44602946 44604169 44604518 44604882 44605165 44605480 44608440 44609185 44609199 44609226 44609238 44609781 44610104 44611409 44611875 44611977 44612067 44612232 44612233 44612359 44613643 44613730 44613813 44613830 44613846 44614438 44616227 44616251 44616844 44617096 44617639 44618619 44619747 44620253 44620739 44621197 44621283 44621876 44622040 44622049 44622478 44622782 44622847 44622888 44623511 44623613 44623802 44623946 44624124 44626632 44626682 44627087 44627553 44628114 44628299 44629507 44630328 44631075 44631282 44631591 44632263 44632613 44633232 44633301 44633570 44636285 44636468 44636689 44636699 44636996 44637090 44637377 44637979 44638043 44638367 44639063 44639082 44640094 44640257 44640664 44641045 44642933 44643751 44644057 44644892 44646529 44646772 44647109 44647419 44647590 44647670 44651679 44652158 44654393 44654397 44658713 44658968 44659059 44661246 44662376 44662841 44663737 44669150 44670677 44670970 44670985 44671103 44671342 44671815 44671858 44671911 44672847 44673031 44678973 44679032 44679360 44679433 44679895 44680125 44680254 44680498 44680557 44681536 44681595 44681967 44682682 44683970 44684075 44684944 44684967 44685117 44685759 44688469 44688570 44689230 44690624 44690901 44691633 44692476 44692679 44692713 44694327 44695079 44696062 44696946 44697614 44698986 44699688 44699733 44700269 44700322 44701095 44701500 44701571 44701611 44701879 44702374 44702777 44702840 44703628 44704079 44704726 44705063 44705271 44705669 44706115 44706653 44707052 44707280 44708521 44708587 44709224 44710478 44710502 44710824 44711643 44711880 44711924 44712276 44712280 44712321 44712376 44713709 44714819 44715240 44715458 44715816 44716205 44716347 44717008 44717497 44717521 44717980 44718651 44718859 44719169 44719791 44719812 44720794 44720844 44721230 44721750 44721758 44721792 44722702 44723176 44723668 44725493 44725708 44725771 44728586 44733528 44734165 44735122 44737268 44737295 44737656 44738067 44738593 44738710 44738775 44739033 44739652 44739738 44739755 44741469 44741556 44742645 44743203 44745867 44748303 44748969 44749577 44753924 44759378 44761152 44762220 44765343 44766306 44770484 44771549 44772807 44773734 44776734 44777952 44778032 44779436 44779648 44782263 44784839 44786827 44787140 44791548 44791673 44792431 44792546 44794310 44795229 44795741 44796669 44797659 44799572 44799951 44802504 44803139 44803645 44803869 44803908 44804023 44804458 44805779 44806047 44806270 44806918 44807801 44808229 44808547 44808705 44809267 44809286 44810613 44810699 44810930 44811711 44812214 44812395 44812822 44812935 44813707 44813848 44814227 44814264 44816465 44816479 44817827 44818324 44818385 44818421 44821130 44822466 44822559 44822915 44824978 44825232 44827317 44827713 44827820 44828657 44832413 44832649 44832941 44833016 44833249 44834855 44836510 44836891 44837180 44838979 44839066 44839386 44840360 44840687 44842720 44842803 44842851 44843500 44843679 44844081 44846266 44847691 44847755 44847865 44848631 44848852 44849244 44849740 44850307 44850638 44851942 44852228 44854694 44855012 44861596 44862018 44862523 44864554 44867329 44867343 44867830 44869098 44869517 44872974 44873650 44875403 44875642 44877063 44880912 44880937 44880986 44882009 44882879 44888192 44892318 44895399 44900287 44901581 44901670 44903164 44903930 44906829 44909903 44910324 44910742 44910757 44911620 44914046 44914106 44915256 44915291 44915849 44915949 44916353 44920328 44920620 44922813 44923313 44924288 44924302 44924381 44924788 44924913 44925902 44926519 44927816 44932348 44932982 44933421 44934517 44934910 44936665 44938771 44939307 44939459 44940835 44941478 44942012 44942703 44946637 44946869 44947199 44947468 44949545 44950620 44950665 44951725 44953475 44956036 44956087 44956551 44963197 44963498 44965442 44966303 44968461 44968680 44969675 44972134 44973034 44973420 44973858 44973982 44974096 44974262 44974633 44976934 44977104 44978515 44980122 44980782 44981362 44982157 44982166 44982310 44982732 44983408 44983694 44984077 44984102 44984614 44984712 44984950 44984955 44985183 44986269 44986774 44986923 44987170 44987347 44987396 44988068 44993169 44998668 45001251 45004509 45004612 45005912 45006805 45006934 45007539 45011169 45012981 45013938 45017939 45019551 45019771 45020342 45021609 45023407 45024015 45026182 45027467 45027567 45028769 45030615 45030877 45031270 45032312 45032405 45032455 45033344 45035920 45037088 45039526 45039883 45041300 45045581 45046733 45046877 45047061 45047468 45047973 45048552 45049168 45049882 45052846 45053328 45054870 45056542 45057204 45057491 45058080 45058501 45058648 45059571 45060871 45061706 45061904 45064927 45066456 45069161 45070713 45071490 45071675 45071807 45072798 45073223 45074611 45074616 45074805 45075316 45075449 45076146 45080902 45089730 45095669 45099063 45099516 45101030 45103608 45105620 45107111 45107125 45107895 45109522 45109968 45111303 45112310 45116187 45120759 45123014 45123679 45129014 45129767 45130386 45131556 45137538 45137992 45138177 45138224 45140568 45140634 45140974 45141537 45141676 45142210 45143099 45143737 45146446 45146711 45147609 45147612 45147790 45148024 45148461 45150460 45150653 45151780 45152780 45154432 45156597 45157007 45157044 45161435 45162224 45162599 45168204 45174376 45176303 45178970 45180932 45181734 45183440 45188568 45191247 45191392 45191804 45192577 45195198 45199492 45200778 45200799 45201031 45201190 45204069 45205086 45211922 45214349 45214611 45214652 45214933 45215157 45215573 45216497 45217426 45217755 45220492 45220653 45220658 45222385 45222902 45223770 45224791 45225925 45225935 45225953 45226120 45227655 45227692 45227809 45228250 45229879 45230169 45230973 45231571 45231720 45232461 45232578 45232586 45232637 45232668 45232706 45234025 45234625 45235969 45237158 45238283 45238842 45239752 45241278 45245768 45251808 45253595 45255421 45256145 45256360 45256783 45259292 45260029 45260405 45261041 45263411 45264032 45265249 45266852 45266997 45267234 45267453 45269132 45269239 45269285 45271294 45272265 45272458 45275769 45277540 45277553 45279097 45280172 45280721 45280735 45280882 45281369 45281750 45281858 45283281 45285216 45287267 45287647 45287983 45288553 45289177 45290920 45291342 45292326 45294248 45294262 45295408 45296930 45298546 45298558 45298693 45299226 45300227 45302233 45302505 45302516 45303000 45303032 45303375 45303387 45304862 45305042 45305543 45305681 45305942 45307275 45307517 45307818 45308315 45308319 45309004 45309202 45309242 45309302 45309346 45309668 45309822 45309935 45311157 45311541 45311610 45311849 45312363 45312407 45312432 45312462 45313248 45313418 45313438 45313970 45314202 45314509 45314793 45315383 45316643 45316954 45317176 45317669 45318408 45318661 45318681 45319244 45320240 45320777 45321506 45321509 45321858 45321931 45322589 45322625 45322875 45323211 45323492 45323641 45324155 45324901 45324925 45325068 45325193 45325859 45326207 45326552 45326605 45326701 45328712 45330372 45331779 45333333 45338254 45347514 45348923 45352603 45352954 45354060 45356845 45358025 45359209 45359676 45359782 45359914 45362251 45363064 45363959 45363990 45366416 45367537 45368454 45368685 45369182 45369928 45370115 45371798 45372666 45373220 45373625 45375761 45375924 45378804 45380859 45382068 45382607 45384963 45384975 45385120 45385722 45386525 45388301 45389737 45390580 45390691 45391957 45392051 45392453 45392530 45392634 45392827 45394546 45396793 45397193 45397233 45397661 45397983 45398067 45398393 45400274 45400587 45400990 45401027 45401048 45401067 45401262 45401299 45401828 45401905 45402371 45402651 45402922 45403621 45403631 45403936 45404129 45404144 45404867 45405473 45406757 45406814 45406991 45408444 45408520 45408661 45408942 45408958 45409335 45409383 45409636 45409676 45409690 45410029 45410751 45411090 45411668 45412884 45413136 45413269 45413328 45413895 45413949 45414511 45415759 45416158 45416372 45416522 45416536 45416702 45416866 45417225 45417493 45417592 45417779 45417787 45418225 45418884 45418903 45419567 45420111 45420546 45421490 45423218 45423426 45423651 45423733 45423743 45425988 45431301 45436066 45436563 45438895 45439094 45441127 45443216 45445437 45445699 45446899 45447049 45448294 45449112 45450166 45450758 45451128 45451478 45452022 45452997 45453382 45454822 45455389 45457136 45459773 45461259 45461380 45463076 45463164 45463381 45464978 45465709 45466639 45467051 45469638 45469734 45471521 45472064 45472661 45473160 45473401 45475219 45478539 45479393 45479542 45479608 45479682 45479700 45480324 45481069 45481487 45481651 45481943 45482347 45482384 45483894 45484271 45484301 45484395 45484469 45484854 45485096 45485137 45485158 45485803 45487487 45487510 45487588 45489786 45491764 45492173 45492177 45492404 45492623 45492682 45492688 45492968 45494009 45494790 45494860 45495040 45495305 45495352 45495554 45495575 45495598 45495963 45496505 45496730 45497106 45497621 45498665 45499048 45499442 45499545 45499656 45499859 45501038 45501055 45501313 45503082 45503287 45503300 45503316 45503512 45503520 45503532 45503613 45504049 45506078 45506452 45506474 45506860 45506995 45507742 45507841 45510059 45510694 45511902 45512122 45512530 45512894 45516934 45517336 45518359 45520201 45527912 45529322 45530571 45532809 45534345 45535885 45536290 45536364 45537262 45537763 45537799 45538205 45539556 45545015 45545880 45545908 45546162 45548427 45548726 45548787 45551089 45551117 45551228 45551300 45552282 45552749 45552849 45553179 45554502 45556021 45556167 45556204 45556232 45556546 45556592 45557385 45557626 45557681 45558634 45558789 45559798 45560970 45561886 45561996 45562022 45562105 45562901 45562945 45565136 45566129 45566185 45567372 45567938 45568325 45568380 45569734 45570513 45571578 45572332 45572435 45572600 45572659 45572830 45572953 45573798 45573860 45574670 45574804 45575043 45577656 45577874 45577943 45578227 45578778 45578971 45579589 45579943 45579947 45580215 45580242 45580373 45580550 45580889 45580904 45581049 45581118 45581144 45581322 45581748 45581804 45581978 45582248 45582411 45582718 45582724 45582731 45583040 45583400 45583636 45584890 45585362 45585989 45585997 45586891 45586892 45586894 45586895 45586993 45587868 45587998 45588492 45588898 45589557 45590193 45590918 45591142 45591845 45591953 45592404 45592408 45592411 45593733 45595411 45595418 45596381 45596641 45597074])
(def filename "loans.csv")
(spit filename "id;status\n")

(defn write-to-file-success [status id]
  (if-not (nil? status)
    (spit filename (str id ";" status "\n") :append true)
  ))

(defn write-to-file-error [id error]
  (spit filename (str id ";" error "\n") :append true))

(defn process-response [res id]
  (if (== (get res :status) 200)
    (write-to-file-success (get (get res :body) :id) id)
    (write-to-file-error id (str "HTTP code " (get res :status) "returned"))
  ))

(defn get-status [id]
  (try
    (process-response
      (client/get
        (str "https://api.vostbank.ru/v1/request/credit/" id "/status?api_token=a06127e3-d1b9-4cd1-a6c2-13b4d068d6bb")
        {:as :json}
      )
      id
    )
    (catch Exception e (write-to-file-error id (str "Http exception: " (.getMessage e))))
  ))

(defn -main
  "Now you are thinking with portals!"
  [& args]
  (run! get-status loans)
  )
