# AutoML using Transfer and Semi-Supervised Learning

This repository includes the code, application, and notebooks for the work "AutoML using Transfer and Semi-Supervised Learning". 
The tools presented here can be employed to construct image classification models with small datasets using transfer learning.
In addition, we present a new semi-supervised learning procedure for constructing image classification models with partially 
annotated datasets. 

## Workflow

The workflow of our method is summarised in the following figure, and is described in detail in a [draft paper](assets/draft.pdf).

![workflow](assets/workflow.jpg)

## Notebooks

The workflow can be executed using with a user's dataset by adapting a [Colab notebook](). Such a notebook can be run also locally 
provided that the user has installed the Python packages indicated in the [requirements file](assets/requirementsFastAI.txt).

## Application

We have also developed a Java application that guides the user in all the stages of the construction of an image classification model using our workflow; 
namely, it helps to annotate the images, train a model, test it, and finally use it. The source code of this application is provided
in this repository, and the application can be downloaded from the following [link]().

## Experiments

We have conducted several experiments with our method. A detailed description of such experiments is provided in the [draft paper](assets/draft.pdf).

### Comparison with other AutoML tools

We have compared our method with several AutoML tools using [several datasets](https://ome.grc.nia.nih.gov/iicbu2008/).

#### Results with partially annotated datasets



A java application that allows users to annotate datasets and creates classification models automatically.
