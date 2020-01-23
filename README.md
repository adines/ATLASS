# AutoML using Transfer and Semi-Supervised Learning

This repository includes the code, application, and notebooks for the work "AutoML using Transfer and Semi-Supervised Learning". 
The tools presented here can be employed to construct image classification models with small datasets using transfer learning.
In addition, we present a new semi-supervised learning procedure for constructing image classification models with partially 
annotated datasets. 

## Workflow

The workflow of our method is summarised in the following figure, and it is described in detail in a [draft paper](assets/draft.pdf).

![workflow](assets/workflow.jpg)

## Notebooks

The workflow can be executed using with a user's dataset by adapting a [Colab notebook](https://colab.research.google.com/drive/1U303O_dBDzIDnPxasaTXEsJS1vgUmXse). Such a notebook can be run also locally 
provided that the user has installed the Python packages indicated in the [requirements file](assets/requirementsFastAI.txt).

## Application

We have also developed a Java application that guides the user in all the stages of the construction of an image classification model using our workflow; 
namely, it helps to annotate the images, train a model, test it, and finally use it. The source code of this application is provided
in this repository, and the application can be downloaded from the following [link](https://github.com/adines/AnnotationTool/releases/download/v1.0/AnnotationTool-1.0.jar).

## Experiments

We have conducted several experiments with our method. A detailed description of such experiments is provided in the [draft paper](assets/draft.pdf).

### Comparison with other AutoML tools

We have compared our method with several AutoML tools using [11 small datasets](https://ome.grc.nia.nih.gov/iicbu2008/). The results are provided in the following table.

|| Binu | C Eleg | Cho | Hela | Liver aging | Liver (AL) | Liver (CR) | Lymp | Pollen| RNAI | Term | Time (min) |
|-----------|-----------|-----------|-----------|-----------|-----------|-----------|-----------|-----------|-----------|-----------|-----------|-----------|
 [AutoKeras](https://autokeras.com/) | 0.55 | 0.66 | 0.96 | 0.47 | 0.91 | 0.98 | 1.00 | 0.89 | 0.81 | 0.24 | 0.47 | 30 |
 [Devol](https://github.com/joeddav/devol) | 0.54 | 0.69 | 0.75 | 0.68 | 0.43 | 0.82 | 1.00 | 0.55 | 0.89 | 0.28 | 0.36 | 16 |
 [Ludwig](https://uber.github.io/ludwig/) | 0.54 | 0.48 | 0.64 | 0.51 | 0.33 | 0.65 | 0.93 | 0.57 | 0.58 | 0 | 0.53 | 31 |
 [WND-CHARM](https://github.com/wnd-charm/wnd-charm) | 1.00 | 0.7 | 0.95 | 0.88 | 0.93 | 0.98 | 0.97 | 0.79 | 0.96 | 0.66 | 0.45 | 53 |
||||||||||||||
Ours | 1.00 | 1.00 | 0.95 | 0.98 | 0.97 | 1.00 | 1.00 | 0.91 | 0.96 | 0.74 | 0.72 | 16 |

Colab notebooks for the experiments using our method:
- [Binucleate](https://colab.research.google.com/drive/1PHY1fLevi040LJKQk73xnJnNfMsSvvV_).
- [C. Elegans](https://colab.research.google.com/drive/18kyQDcbOzMdrTLdmLwU6pUJiSNmGwz4B).
- [Cho](https://colab.research.google.com/drive/1rgz5U2FZYZpsuK0fBWW1VfetVxWt9FXq).
- [Hela](https://colab.research.google.com/drive/1ffZXAlOCwhPhIxbiFUsWS23hl19EA9rd).
- [Liver aging](https://colab.research.google.com/drive/1YODqONRwaltgm9GojjEJXXio2mmzQS1l).
- [Liver gender (AL)](https://colab.research.google.com/drive/1Mv74k9i2-JWHg_18C5Nt_XX5MjhuyAce).
- [Liver gender (CR)](https://colab.research.google.com/drive/1zKwJDZoWvzCzHeMxFh2Y45zfEaZD4h9K).
- [Lymphoma](https://colab.research.google.com/drive/14ei4d10EGLJRpo5IN2l-XYevqqAJ6_Nc).
- [Pollen](https://colab.research.google.com/drive/1NBZOhA1_DRZRYE2EPdmWl0E27Z9adF8y).
- [RNAI](https://colab.research.google.com/drive/1LpN38mM1jSGxg5_6BXL3w_UaZJR08bGC).
- [Terminal Bulb aging](https://colab.research.google.com/drive/1fjz7-E_0HdFAO7f4STAdCssy_H357WAN).

### Results with partially annotated datasets

We have also shown the benefits of using our semi-supervised learning method in three different datasets and taking differents amounts of annotated images; namely 25, 50 and 75 images per class. 


#### [Blindness](https://www.kaggle.com/c/aptos2019-blindness-detection/overview/description)

Size of dataset: 3662 images. Number of classes: 5.

Best accuracy using the whole training set (2746 images): 0.83

Results using part of the dataset and applying our method with different strategies. 

  | | N.D. | D.D. | I.D.D. | M.D. | I.M.D. | M.D.D. | I.M.D.D. |
|------|------|------|------|------|------|------|------|
 [25 per class](https://colab.research.google.com/drive/1HFlkFkofpQ4HtwDR6PiHeslK1F1O1KA0) | 0.66 | 0.71 | **0.73** | 0.70 | 0.72 | 0.70 | 0.73 |
 [50 per class](https://colab.research.google.com/drive/14GmaCop3gRhweSnuBu6r71inUngIPPEt) | 0.71 | 0.76  | **0.78** | 0.72 | 0.71 | 0.74 | 0.76 |
 [75 per class](https://colab.research.google.com/drive/1HdzApNEmw2Zc9t9zJggMbmnyXWTsctCu) | 0.72 | 0.76 | 0.76 | 0.75 | **0.76** | 0.75 | 0.76|


#### [Chest X Ray](http://www.sciencedirect.com/science/article/pii/S0092867418301545)

Size of dataset: 2355 images. Number of classes: 2.

Best accuracy using the whole training set (1649 images): 0.93

Results using part of the dataset and applying our method with different strategies. 

  | | N.D. | D.D. | I.D.D. | M.D. | I.M.D. | M.D.D. | I.M.D.D. |
|------|------|------|------|------|------|------|------|
 [25 per class](https://colab.research.google.com/drive/1cG4i3ZuY5yaMSjinrILwGY-7OTlQR609) | 0.73 | 0.73 | 0.78 | 0.74 | 0.74 | 0.76 | **0.83** |
 [50 per class](https://colab.research.google.com/drive/1OyeoYhKBloEuu_qYbMOYN6diecaffQrA) | 0.85 | 0.91  | 0.90 | 0.88 | **0.92** | 0.89 | 0.88 |
 [75 per class](https://colab.research.google.com/drive/1DGd5yhHXr8LYORGDKP3Jf1oMLkCc0p7x) | 0.87 | 0.88 | 0.83 | 0.90 | **0.91** | 0.90 | 0.90|


#### [Fungi](https://link.springer.com/article/10.1007%2Fs00500-019-03832-8)

Size of dataset: 2355 images. Number of classes: 2.

Best accuracy using the whole training set (1649 images): 0.96

Results using part of the dataset and applying our method with different strategies. 

  | | N.D. | D.D. | I.D.D. | M.D. | I.M.D. | M.D.D. | I.M.D.D. |
|------|------|------|------|------|------|------|------|
 [25 per class](https://colab.research.google.com/drive/1LvI1gHVpAzkOZ6ltnaRCd3MS7YtdhBXr) | 0.74 | 0.69 | 0.73 | 0.74 | 0.74 | 0.74 | **0.75** |
 [50 per class](https://colab.research.google.com/drive/1YrYvrsQ3N_5wReaVaWwLhxIxNSkY_8Mb) | 0.80 | 0.82  | 0.79 | 0.82 | 0.83 | **0.84** | 0.83 |
 [75 per class](https://colab.research.google.com/drive/1qlnbpKKVobw8aaWJ6qfD8IZi7FARhxfy) | 0.90 | 0.87 | 0.89 | 0.91 | 0.91 | 0.91 | **0.92**|
 
 
 #### [HAM10000](https://www.nature.com/articles/sdata2018161)

Size of dataset: 10015 images. Number of classes: 7.

Best accuracy using the whole training set (7511 images): 0.88

Results using part of the dataset and applying our method with different strategies. 

  | | N.D. | D.D. | I.D.D. | M.D. | I.M.D. | M.D.D. | I.M.D.D. |
|------|------|------|------|------|------|------|------|
 [25 per class](https://colab.research.google.com/drive/1dA8OCpL2uIcgXsC566f4_nYA_6rjyX_2) | 0.55 | 0.61 | **0.65** | 0.60 | 0.63 | 0.63 | 0.63 |
 [50 per class](https://colab.research.google.com/drive/1sSLw5t_iDF8uHDyax5rMwsXVvu_4F9fi) | 0.63 | 0.67  | **0.72** | 0.64 | 0.66 | 0.65 | 0.66 |
 [75 per class](https://colab.research.google.com/drive/1TIpG-duY78DJlDmfmkDQ3jnWrQY1HMYX) | 0.64 | 0.69 | **0.74** | 0.69 | 0.72 | 0.69 | 0.74|
 
 #### [ISIC dataset](https://arxiv.org/abs/1902.03368)

Size of dataset: 1500 images. Number of classes: 7.

Best accuracy using the whole training set (1125 images): 0.8724

Results using part of the dataset and applying our method with different strategies. 

  | | N.D. | D.D. | I.D.D. | M.D. | I.M.D. | M.D.D. | I.M.D.D. |
|------|------|------|------|------|------|------|------|
 [25 per class](https://colab.research.google.com/drive/1dA8OCpL2uIcgXsC566f4_nYA_6rjyX_2) | 0.75 | 0.78 | 0.78 | 0.78 | **0.84** | 0.80 | 0.83 |
 [50 per class](https://colab.research.google.com/drive/1sSLw5t_iDF8uHDyax5rMwsXVvu_4F9fi) | 0.81 | 0.81  | 0.84 | 0.82 | 0.84 | 0.83 | **0.85** |
 [75 per class](https://colab.research.google.com/drive/1TIpG-duY78DJlDmfmkDQ3jnWrQY1HMYX) | 0.84 | 0.83 | 0.86 | 0.85 | **0.88** | 0.84 | 0.87|
 
 
 #### [Kvasir dataset](http://doi.acm.org/10.1145/3083187.3083212)

Size of dataset: 8000 images. Number of classes: 8.

Best accuracy using the whole training set (6000 images): 0.9340

Results using part of the dataset and applying our method with different strategies. 

  | | N.D. | D.D. | I.D.D. | M.D. | I.M.D. | M.D.D. | I.M.D.D. |
|------|------|------|------|------|------|------|------|
 [25 per class](https://colab.research.google.com/drive/1bSCiR25a4AQ1-JNL8cIQESXXAeLcXzKC) | 0.80 | 0.85 | 0.89 | 0.88 | 0.89 | 0.88 | **0.90**|
 [50 per class](https://colab.research.google.com/drive/1ACV4JzssjNUn-0J6IEei65j625QT7MKZ) | 0.84 | 0.87 | 0.89 | 0.89 | **0.90** | 0.89 | 0.90|
 [75 per class](https://colab.research.google.com/drive/1B5q-Y7yDM9zsxi9zw1v2bdbnKZP3UHNk) | 0.87 | 0.90 | 0.92 | 0.91 | 0.92 | 0.91 | **0.92**|


 #### [Open Sprayer](https://www.kaggle.com/gavinarmstrong/open-sprayer-images)

Size of dataset: 6697 images. Number of classes: 2.

Best accuracy using the whole training set (5023 images): 0.97

Results using part of the dataset and applying our method with different strategies. 

  | | N.D. | D.D. | I.D.D. | M.D. | I.M.D. | M.D.D. | I.M.D.D. |
|------|------|------|------|------|------|------|------|
 [25 per class](https://colab.research.google.com/drive/1dA8OCpL2uIcgXsC566f4_nYA_6rjyX_2) | 0.84 | 0.90 | 0.91 | 0.83 | 0.84 | 0.85 | **0.93** |
 [50 per class](https://colab.research.google.com/drive/1sSLw5t_iDF8uHDyax5rMwsXVvu_4F9fi) | 0.87 | 0.86  | 0.90 | 0.90 | 0.91 | 0.91 | **0.92** |
 [75 per class](https://colab.research.google.com/drive/1TIpG-duY78DJlDmfmkDQ3jnWrQY1HMYX) | 0.90 | 0.92 | 0.94 | 0.94 | 0.94 | 0.94 | **0.94**|


#### [Plant Seedlings dataset](https://arxiv.org/abs/1711.05458)

Size of dataset: 6000 images. Number of classes: 12.

Best accuracy using the whole training set (4125 images): 0.9616

Results using part of the dataset and applying our method with different strategies. 

  | | N.D. | D.D. | I.D.D. | M.D. | I.M.D. | M.D.D. | I.M.D.D. |
|------|------|------|------|------|------|------|------|
  [25 per class](https://colab.research.google.com/drive/1U303O_dBDzIDnPxasaTXEsJS1vgUmXse) | 0.83 | 0.87 | 0.89 | 0.88 | **0.92** | 0.88 | 0.91|
  [50 per class](https://colab.research.google.com/drive/1fgj4VFirrZ37LFZKifEZCpsN9EKG0XYx) | 0.89 | 0.92 | **0.94** | 0.93 | 0.93 | 0.93 | 0.93|
  [75 per class](https://colab.research.google.com/drive/14P13GVlU2ux1EQedoNK762j7XYp0rXWI) | 0.91 | 0.93 | 0.94 | 0.94 | **0.95** | 0.94 | 0.95|
  
  

#### [Retinal OCT](http://www.sciencedirect.com/science/article/pii/S0092867418301545)

Size of dataset: 84484 images. Number of classes: 4.

Best accuracy using the whole training set (63363 images): 0.99

Results using part of the dataset and applying our method with different strategies. 

  | | N.D. | D.D. | I.D.D. | M.D. | I.M.D. | M.D.D. | I.M.D.D. |
|------|------|------|------|------|------|------|------|
 [25 per class](https://colab.research.google.com/drive/1dA8OCpL2uIcgXsC566f4_nYA_6rjyX_2) | 0.90 | 0.90 | 0.86 | 0.93 | **0.96** | 0.93 | 0.94 |
 [50 per class](https://colab.research.google.com/drive/1sSLw5t_iDF8uHDyax5rMwsXVvu_4F9fi) | 0.93 | 0.95  | 0.95 | 0.96 | **0.98** | 0.97 | 0.97 |
 [75 per class](https://colab.research.google.com/drive/1TIpG-duY78DJlDmfmkDQ3jnWrQY1HMYX) | 0.94 | 0.97 | 0.93 | 0.98 | **0.99** | 0.98 | 0.98|
 
 
 #### [Tobacco](http://www.sciencedirect.com/science/article/pii/S0167865513004224)

Size of dataset: 3492 images. Number of classes: 10.

Best accuracy using the whole training set (2619 images): 0.86

Results using part of the dataset and applying our method with different strategies. 

  | | N.D. | D.D. | I.D.D. | M.D. | I.M.D. | M.D.D. | I.M.D.D. |
|------|------|------|------|------|------|------|------|
 [25 per class](https://colab.research.google.com/drive/1dA8OCpL2uIcgXsC566f4_nYA_6rjyX_2) | 0.66 | 0.69 | 0.70 | 0.74 | **0.76** | 0.72 | 0.74 |
 [50 per class](https://colab.research.google.com/drive/1sSLw5t_iDF8uHDyax5rMwsXVvu_4F9fi) | 0.72 | 0.75  | 0.77 | 0.77 | **0.80** | 0.79 | 0.76 |
 [75 per class](https://colab.research.google.com/drive/1TIpG-duY78DJlDmfmkDQ3jnWrQY1HMYX) | 0.78 | 0.81 | **0.84** | 0.81 | 0.81 | 0.81 | 0.79|
