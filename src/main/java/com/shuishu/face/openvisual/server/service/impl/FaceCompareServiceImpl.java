package com.shuishu.face.openvisual.server.service.impl;

import com.shuishu.face.openvisual.core.domain.ExtParam;
import com.shuishu.face.openvisual.core.domain.FaceImage;
import com.shuishu.face.openvisual.core.domain.FaceInfo;
import com.shuishu.face.openvisual.core.domain.ImageMat;
import com.shuishu.face.openvisual.core.extract.FaceFeatureExtractor;
import com.shuishu.face.openvisual.server.entity.dto.FaceCompareDto;
import com.shuishu.face.openvisual.server.entity.extend.CompareFace;
import com.shuishu.face.openvisual.server.entity.extend.FaceLocation;
import com.shuishu.face.openvisual.server.entity.vo.FaceCompareVo;
import com.shuishu.face.openvisual.server.service.FaceCompareService;
import com.shuishu.face.openvisual.utils.Similarity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FaceCompareServiceImpl implements FaceCompareService {

    @Value("${face.face-mask.face-compare:false}")
    private boolean faceMask;

    private final FaceFeatureExtractor faceFeatureExtractor;


    @Override
    public FaceCompareVo faceCompare(FaceCompareDto faceCompareDto) {
        FaceInfo faceInfoA = getFaceInfo(faceCompareDto.getFaceScoreThreshold(), faceCompareDto.getImageBase64A());
        if (null == faceInfoA) {
            throw new RuntimeException("Image A is not face");
        }
        FaceInfo faceInfoB = getFaceInfo(faceCompareDto.getFaceScoreThreshold(), faceCompareDto.getImageBase64B());
        if (null == faceInfoB) {
            throw new RuntimeException("Image B is not face");
        }
        //计算余弦相似度
        float simVal = Similarity.cosineSimilarityNorm(faceInfoA.embedding.embeds, faceInfoB.embedding.embeds);
        float confidence = (float) Math.floor(simVal * 1000000) / 10000;
        //欧式距离
        float euclideanDistance = Similarity.euclideanDistance(faceInfoA.embedding.embeds, faceInfoB.embedding.embeds);
        float distance = (float) Math.floor(euclideanDistance * 10000) / 10000;
        //构建返回值
        FaceCompareVo faceCompareRep = new FaceCompareVo();
        faceCompareRep.setDistance(distance);
        faceCompareRep.setConfidence(confidence);
        if (faceCompareDto.getNeedFaceInfo()) {
            CompareFace compareFace = new CompareFace();
            compareFace.setFaceScoreA((float) Math.floor(faceInfoA.score * 1000000) / 10000);
            compareFace.setFaceScoreB((float) Math.floor(faceInfoA.score * 1000000) / 10000);
            FaceInfo.FaceBox boxA = faceInfoA.box;
            compareFace.setLocationA(FaceLocation.build(boxA.leftTop.x, boxA.leftTop.y, boxA.width(), boxA.height()));
            FaceInfo.FaceBox boxB = faceInfoB.box;
            compareFace.setLocationB(FaceLocation.build(boxB.leftTop.x, boxB.leftTop.y, boxB.width(), boxB.height()));
            faceCompareRep.setFaceInfo(compareFace);
        }
        //返回对象
        return faceCompareRep;
    }


    /**
     * 图片检测并提取人脸特征
     *
     * @param faceScoreThreshold -
     * @param imageBase64        -
     * @return -
     */
    private FaceInfo getFaceInfo(float faceScoreThreshold, String imageBase64) {
        faceScoreThreshold = faceScoreThreshold < 0 ? 0 : faceScoreThreshold;
        faceScoreThreshold = faceScoreThreshold > 100 ? 100 : faceScoreThreshold;
        faceScoreThreshold = faceScoreThreshold > 1 ? faceScoreThreshold / 100 : faceScoreThreshold;

        ExtParam extParam = ExtParam.build().setMask(faceMask).setScoreTh(faceScoreThreshold).setIouTh(0).setTopK(1);
        ImageMat imageMat = null;
        FaceImage faceImage = null;
        try {
            imageMat = ImageMat.fromBase64(imageBase64);
            faceImage = faceFeatureExtractor.extract(imageMat, extParam, new HashMap<>());
        } finally {
            if (null != imageMat) {
                imageMat.release();
            }
        }
        if (null == faceImage) {
            throw new RuntimeException("FeatureExtractor extract error");
        }
        List<FaceInfo> faceInfos = faceImage.faceInfos();
        if (faceInfos.size() > 0) {
            return faceInfos.get(0);
        }
        return null;
    }


}
