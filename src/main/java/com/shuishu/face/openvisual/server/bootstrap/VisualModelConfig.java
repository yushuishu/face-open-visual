package com.shuishu.face.openvisual.server.bootstrap;

import com.shuishu.face.openvisual.core.base.*;
import com.shuishu.face.openvisual.core.extract.FaceFeatureExtractor;
import com.shuishu.face.openvisual.core.extract.FaceFeatureExtractorImpl;
import com.shuishu.face.openvisual.core.models.*;
import com.shuishu.face.openvisual.utils.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("visualModelConfig")
public class VisualModelConfig {

    @Value("${face.model.base-model-path}")
    private String baseModelPath;

    @Value("${face.model.face-detection.name}")
    private String faceDetectionName;
    @Value("${face.model.face-detection.model-path}")
    private String[] faceDetectionModel;
    @Value("${face.model.face-detection.thread:4}")
    private Integer faceDetectionThread;

    @Value("${face.model.face-detection.backup.name}")
    private String backupFaceDetectionName;
    @Value("${face.model.face-detection.backup.model-path}")
    private String[] backupFaceDetectionModel;
    @Value("${face.model.face-detection.backup.thread:4}")
    private Integer backupFaceDetectionThread;

    @Value("${face.model.faceKey-point.name:InsightCoordFaceKeyPoint}")
    private String faceKeyPointName;
    @Value("${face.model.faceKey-point.model-path}")
    private String[] faceKeyPointModel;
    @Value("${face.model.faceKey-point.thread:4}")
    private Integer faceKeyPointThread;

    @Value("${face.model.face-alignment.name:Simple005pFaceAlignment}")
    private String faceAlignmentName;

    @Value("${face.model.face-recognition.name:InsightArcFaceRecognition}")
    private String faceRecognitionName;
    @Value("${face.model.face-recognition.model-path}")
    private String[] faceRecognitionNameModel;
    @Value("${face.model.face-recognition.thread:4}")
    private Integer faceRecognitionNameThread;


    @Value("${face.model.face-attribute.name:InsightAttributeDetection}")
    private String faceAttributeDetectionName;
    @Value("${face.model.face-attribute.model-path}")
    private String[] faceAttributeDetectionNameModel;
    @Value("${face.model.face-attribute.thread:4}")
    private Integer faceAttributeDetectionNameThread;


    /**
     * 获取人脸识别模型
     *
     * @return -
     */
    @Bean(name = "visualFaceDetection")
    public FaceDetection getFaceDetection() {
        if ("PcnNetworkFaceDetection".equalsIgnoreCase(faceDetectionName)) {
            return new PcnNetworkFaceDetection(getModelPath(faceDetectionName, faceDetectionModel), faceDetectionThread);
        } else if ("InsightScrfdFaceDetection".equalsIgnoreCase(faceDetectionName)) {
            return new InsightScrfdFaceDetection(getModelPath(faceDetectionName, faceDetectionModel)[0], faceDetectionThread);
        } else {
            return new PcnNetworkFaceDetection(getModelPath(faceDetectionName, faceDetectionModel), faceDetectionThread);
        }
    }

    /**
     * 获取人脸识别模型
     *
     * @return -
     */
    @Bean(name = "visualBackupFaceDetection")
    public FaceDetection getBackupFaceDetection() {
        if ("PcnNetworkFaceDetection".equalsIgnoreCase(backupFaceDetectionName)) {
            return new PcnNetworkFaceDetection(getModelPath(backupFaceDetectionName, backupFaceDetectionModel), backupFaceDetectionThread);
        } else if ("InsightScrfdFaceDetection".equalsIgnoreCase(backupFaceDetectionName)) {
            return new InsightScrfdFaceDetection(getModelPath(backupFaceDetectionName, backupFaceDetectionModel)[0], backupFaceDetectionThread);
        } else {
            return this.getFaceDetection();
        }
    }

    /**
     * 关键点标记服务
     *
     * @return -
     */
    @Bean(name = "visualFaceKeyPoint")
    public FaceKeyPoint getFaceKeyPoint() {
        if ("InsightCoordFaceKeyPoint".equalsIgnoreCase(faceKeyPointName)) {
            return new InsightCoordFaceKeyPoint(getModelPath(faceKeyPointName, faceKeyPointModel)[0], faceKeyPointThread);
        } else {
            return new InsightCoordFaceKeyPoint(getModelPath(faceKeyPointName, faceKeyPointModel)[0], faceKeyPointThread);
        }
    }

    /**
     * 人脸对齐服务
     *
     * @return -
     */
    @Bean(name = "visualFaceAlignment")
    public FaceAlignment getFaceAlignment() {
        if ("Simple005pFaceAlignment".equalsIgnoreCase(faceAlignmentName)) {
            return new Simple005pFaceAlignment();
        } else if ("Simple106pFaceAlignment".equalsIgnoreCase(faceAlignmentName)) {
            return new Simple106pFaceAlignment();
        } else {
            return new Simple005pFaceAlignment();
        }
    }

    /**
     * 人脸特征提取服务
     *
     * @return -
     */
    @Bean(name = "visualFaceRecognition")
    public FaceRecognition getFaceRecognition() {
        if ("InsightArcFaceRecognition".equalsIgnoreCase(faceRecognitionName)) {
            return new InsightArcFaceRecognition(getModelPath(faceRecognitionName, faceRecognitionNameModel)[0], faceRecognitionNameThread);
        } else if ("SeetaFaceOpenRecognition".equalsIgnoreCase(faceRecognitionName)) {
            return new SeetaFaceOpenRecognition(getModelPath(faceRecognitionName, faceRecognitionNameModel)[0], faceRecognitionNameThread);
        } else {
            return new InsightArcFaceRecognition(getModelPath(faceRecognitionName, faceRecognitionNameModel)[0], faceRecognitionNameThread);
        }
    }

    /**
     * 人脸属性检测
     *
     * @return -
     */
    @Bean(name = "visualAttributeDetection")
    public InsightAttributeDetection getAttributeDetection() {
        if ("InsightAttributeDetection".equalsIgnoreCase(faceAttributeDetectionName)) {
            return new InsightAttributeDetection(getModelPath(faceAttributeDetectionName, faceAttributeDetectionNameModel)[0], faceAttributeDetectionNameThread);
        } else {
            return new InsightAttributeDetection(getModelPath(faceAttributeDetectionName, faceAttributeDetectionNameModel)[0], faceAttributeDetectionNameThread);
        }
    }

    /**
     * 构建特征提取器
     *
     * @param faceDetection   人脸识别模型
     * @param faceKeyPoint    人脸关键点模型
     * @param faceAlignment   人脸对齐模型
     * @param faceRecognition 人脸特征提取模型
     * @return -
     */
    @Bean(name = "visualFaceFeatureExtractor")
    public FaceFeatureExtractor getFaceFeatureExtractor(
            @Qualifier("visualFaceDetection") FaceDetection faceDetection,
            @Qualifier("visualBackupFaceDetection") FaceDetection backupFaceDetection,
            @Qualifier("visualFaceKeyPoint") FaceKeyPoint faceKeyPoint,
            @Qualifier("visualFaceAlignment") FaceAlignment faceAlignment,
            @Qualifier("visualFaceRecognition") FaceRecognition faceRecognition,
            @Qualifier("visualAttributeDetection") FaceAttribute faceAttribute
    ) {
        if (faceDetection.getClass().isAssignableFrom(backupFaceDetection.getClass())) {
            return new FaceFeatureExtractorImpl(
                    faceDetection, null, faceKeyPoint,
                    faceAlignment, faceRecognition, faceAttribute
            );
        } else {
            return new FaceFeatureExtractorImpl(
                    faceDetection, backupFaceDetection, faceKeyPoint,
                    faceAlignment, faceRecognition, faceAttribute
            );
        }
    }

    /**
     * 获取模型路径
     *
     * @param modelName 模型名称
     * @return -
     */
    private String[] getModelPath(String modelName, String modelPath[]) {
        String basePath = "face-search-core/src/main/resources/sdk/";
        if (StringUtils.isNotEmpty(this.baseModelPath)) {
            basePath = this.baseModelPath;
            basePath = basePath.replaceAll("^\'|\'$", "");
            basePath = basePath.replaceAll("^\"|\"$", "");
            basePath = basePath.endsWith("/") ? basePath : basePath + "/";
        }

        if ((null == modelPath || modelPath.length != 3) && "PcnNetworkFaceDetection".equalsIgnoreCase(modelName)) {
            return new String[]{
                    basePath + "model/onnx/detection_face_pcn/pcn1_sd.onnx",
                    basePath + "model/onnx/detection_face_pcn/pcn2_sd.onnx",
                    basePath + "model/onnx/detection_face_pcn/pcn3_sd.onnx"
            };
        }

        if ((null == modelPath || modelPath.length != 1) && "InsightScrfdFaceDetection".equalsIgnoreCase(modelName)) {
            return new String[]{basePath + "model/onnx/detection_face_scrfd/scrfd_500m_bnkps.onnx"};
        }

        if ((null == modelPath || modelPath.length != 1) && "InsightCoordFaceKeyPoint".equalsIgnoreCase(modelName)) {
            return new String[]{basePath + "model/onnx/keypoint_coordinate/coordinate_106_mobilenet_05.onnx"};
        }

        if ((null == modelPath || modelPath.length != 1) && "InsightArcFaceRecognition".equalsIgnoreCase(modelName)) {
            return new String[]{basePath + "model/onnx/recognition_face_arc/glint360k_cosface_r18_fp16_0.1.onnx"};
        }

        if ((null == modelPath || modelPath.length != 1) && "SeetaFaceOpenRecognition".equalsIgnoreCase(modelName)) {
            return new String[]{basePath + "model/onnx/recognition_face_seeta/face_recognizer_512.onnx"};
        }

        if ((null == modelPath || modelPath.length != 1) && "InsightAttributeDetection".equalsIgnoreCase(modelName)) {
            return new String[]{basePath + "model/onnx/attribute_gender_age/insight_gender_age.onnx"};
        }

        if ((null == modelPath || modelPath.length != 1) && "SeetaMaskFaceKeyPoint".equalsIgnoreCase(modelName)) {
            return new String[]{basePath + "model/onnx/keypoint_seeta_mask/landmarker_005_mask_pts5.onnx"};
        }

        return modelPath;
    }
}
