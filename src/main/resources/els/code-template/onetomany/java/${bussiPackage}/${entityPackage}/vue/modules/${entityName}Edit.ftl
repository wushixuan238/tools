<template>
    <div class="${entityName}Edit">
        <a-spin :spinning="confirmLoading">
            <business-layout
                    :ref="businessRefName"
                    :currentEditRow="currentEditRow"
                    :remoteJsFilePath="remoteJsFilePath"
                    :requestData="requestData"
                    :externalToolBar="externalToolBar"
                    :pageHeaderButtons="pageHeaderButtons"
                    :handleAfterDealSource="handleAfterDealSource"
                    :handleBeforeRemoteConfigData="handleBeforeRemoteConfigData"
                    modelLayout="masterSlave"
                    pageStatus="edit"
                    v-on="businessHandler"
            >
            </business-layout>
            <field-select-modal
                    :isEmit="true"
                    @ok="checkItemSelectOk"
                    ref="fieldSelectModal"/>
        </a-spin>
    </div>
</template>

<script>
    import BusinessLayout from '@comp/template/business/BusinessLayout'
    import fieldSelectModal from '@comp/template/fieldSelectModal'
    import {businessUtilMixin} from '@comp/template/business/businessUtilMixin.js'
    import {getAction, postAction} from '@/api/manage'

    export default {
        name: '${entityName}Edit',
        components: {
            BusinessLayout,
            fieldSelectModal
        },
        mixins: [businessUtilMixin],
        props: {
            currentEditRow: {
                required: true,
                type: Object,
                default: () => {
                    return {}
                }
            }
        },
        data() {
            return {
                confirmLoading: false,
                businessRefName: 'businessRef',
                curGroupCode: '',
                fieldSelectType: '',
                requestData: {
                    detail: {
                        url: '/${entityPackage}/${entityName?uncap_first}/queryById', args: (that) => {
                            return {id: that.currentEditRow.id}
                        }
                    }
                },
                externalToolBar: {
                    itemInfo: [{
                        title: this.$srmI18n(${r'`${this.$getLangAccount()}#i18n_title_add`'}, '新增'),
                        key: 'gridAdd',
                        attrs: {type: 'primary'},
                        click: this.itemGridAdd
                    }, {
                        title: this.$srmI18n(${r'`${this.$getLangAccount()}#i18n_title_delete`'}, '删除'),
                        key: 'gridDelete'
                    }],
                },
                pageHeaderButtons: [
                    {
                        title: this.$srmI18n(${r'`${this.$getLangAccount()}#i18n_title_save`'}, '保存'),
                        args: {
                            url: '/${entityPackage}/${entityName?uncap_first}/edit'
                        },
                        key: 'save',
                        showMessage: true
                    },
                    {
                        title: this.$srmI18n(${r'`${this.$getLangAccount()}#i18n_title_back`'}, '返回'),
                        key: 'goBack'
                    }
                ],
                url: {}
            }
        },
        computed: {
            remoteJsFilePath() {
                let templateNumber = this.currentEditRow.templateNumber
                let templateVersion = this.currentEditRow.templateVersion
                let account = this.currentEditRow.templateAccount || this.currentEditRow.busAccount
                return `${left}account${right}/purchase_${entityPackage}_${left}templateNumber${right}_${left}templateVersion${right}`
            }
        },
        methods: {
            handleBeforeRemoteConfigData() {
                return {
                    groups: [],
                    itemColumns: []
                }
            },
            handleAfterDealSource(pageConfig, resultData) {
                let formModel = pageConfig.groups[0].formModel
                for (let key in resultData) {
                    formModel[key] = resultData[key]
                }
            },
            goBack() {
                this.$parent.hideEditPage()
            },
            //新增
            itemGridAdd({Vue, pageConfig, btn, groupCode}) {
                let itemGrid = this.getItemGridRef(groupCode)
                let {columns = []} = pageConfig.groups.find(n => n.groupCode === groupCode)
                let row = columns
                    .filter(n => n.field)
                    .reduce((acc, obj) => {
                        acc[obj.field] = obj.defaultValue || ''
                        return acc
                    }, {})
                row.printNumber = this.getBusinessExtendData(this.businessRefName).allData.printNumber
                itemGrid.insertAt([row], -1)
            }

        }
    }
</script>