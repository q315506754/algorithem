package com.jiangli.db.tool

/**
 *
 *
 * @author Jiangli
 * @date 2021/4/6 10:44
 */
fun main(args: Array<String>) {
    var str = """
8691
8783
8692
8784
8700
8799
8701
8800
8702
8801
8703
8802
8704
8803
8705
8804
8706
8805
8707
8806
8708
8807
8709
8808
8710
8811
8711
8812
8712
8813
8713
8814
8714
8815
8715
8817
8716
8818
8717
8820
8718
8821
8719
8823
8720
8824
8721
8825
8722
8826
8723
8827
8724
8828
8725
8830
8726
8831
8727
8832
8728
8833
8729
8835
8730
8836
8731
8837
8732
8838
8733
8839
8734
8840
8735
8841
8736
8842
8737
8843
8738
8844
8739
8845
8740
8846
8741
8847
8742
8848
8743
8850
8744
8851
8745
8852
8746
8853
8747
8854
8748
8855
8749
8856
8750
8857
8751
8858
8752
8859
8753
8860
8754
8861
8755
8862
8756
8863
8757
8864
8758
8865
8759
8866
8760
8867
8761
8868
8762
8869
8763
8870
8764
8871
8765
8872
8766
8873
8767
8874
8768
8875
8769
8876
8770
8877
8771
8879
8772
8880
8773
8881
8774
8882
8775
8883
8776
8884
8777
8885
8778
8886
8779
8887
8780
8888

    """.trimIndent()

    var i = 0
    str.split("\n").forEachIndexed { index, it ->
        var line = it.trim()
//        println("${line}/子部${i++}/子子部${i}")

        if (index % 2== 0) {
            println("${line},")
        }
    }

}